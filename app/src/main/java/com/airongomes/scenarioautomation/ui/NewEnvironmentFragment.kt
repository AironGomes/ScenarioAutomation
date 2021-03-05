package com.airongomes.scenarioautomation.ui

import android.Manifest
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.databinding.FragmentNewEnvironmentBinding
import com.airongomes.scenarioautomation.viewModel.NewEnvironmentViewModel
import com.airongomes.scenarioautomation.viewModel.NewEnvironmentViewModelFactory
import com.google.android.material.snackbar.Snackbar


class NewEnvironmentFragment: Fragment() {

    // Variáveis a serem inicializadas no onCreateView
    lateinit var binding: FragmentNewEnvironmentBinding
    lateinit var viewModel: NewEnvironmentViewModel
    lateinit var arguments: NewEnvironmentFragmentArgs

    // Registrar callback para acessar galeria de imagens
    var galleryContent: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback { result ->
                if(result != null) {
                    viewModel.setImageUri(result)
                }
                 })

    // Registrar callback para acessar a câmera
    val cameraContent: ActivityResultLauncher<Uri> = registerForActivityResult(TakePicture(),
            ActivityResultCallback { resultUri ->
                if (resultUri) {
                    imageUri?.let { viewModel.setImageUri(it) }
                }
            })

    // Variável para imagem Uri
    private var imageUri: Uri? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_new_environment,
                container,
                false)

        // Cria uma instância de database e adiciona o projectDao para viewModel
        val application = requireNotNull(this.activity).application
        arguments = NewEnvironmentFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = NewEnvironmentViewModelFactory(application, arguments.environmentId, arguments.projectId)

        // Cria instância do DetailProjectViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewEnvironmentViewModel::class.java)

        binding.viewModel = viewModel

        // ClickListener para botão button_confirm
        binding.buttonConfirm.setOnClickListener { saveEnvironment() }

        // ClickListener para botão button_cancel
        binding.buttonCancel.setOnClickListener { callDetailProjectFragment(arguments.projectId) }

        // ClickListener para botão button_take_picture
        binding.buttonTakePicture.setOnClickListener { dialogChooseImage() }

        // Observar o Livedata closeFragment
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                        binding.viewGroupId,
                        resources.getText(R.string.message_saved),
                        Snackbar.LENGTH_SHORT
                ).show()
                callDetailProjectFragment(arguments.projectId)
                viewModel.closeFragmentObserved()
            }
        })

        // Observar o Livedata closeFragment
        viewModel.environment?.observe(viewLifecycleOwner, Observer {
            if (it.imageUri != null) {
                viewModel.setImageUri(Uri.parse(it.imageUri))
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    /**
     * Verifica se os campos estão preenchidos e chama saveEnvironment de ViewModel
     */
    private fun saveEnvironment() {
        val environmentName = binding.environmentEditText.text.toString()

        if(environmentName.isBlank()) {
            Toast.makeText(
                    requireContext(),
                    getText(R.string.message_environment_name_is_blank),
                    Toast.LENGTH_LONG
            ).show()
        }
        else viewModel.saveEnvironment(environmentName)
    }

    /**
     * Chamar Fragmento: callDetailProjectFragment
     */
    private fun callDetailProjectFragment(projectId: Long) {
        this.findNavController().navigate(NewEnvironmentFragmentDirections
                .actionNewEnvironmentFragmentToDetailProjectFragment(projectId))
    }

    /**
     * Apresenta um dialogo para o usuário escolher entre tirar foto ou escolher imagem da galeria
     */
    private fun dialogChooseImage() {
        val options = arrayOf<CharSequence>(
                getString(R.string.char_sequence_take_photo),
                getString(R.string.char_sequence_open_gallery),
                getString(R.string.char_sequence_cancel)
        )
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_dialog_options_photo))
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            when {
                options[item] == getString(R.string.char_sequence_take_photo) -> {
                    // Requisitar permissão para acessar a câmera
                    requestPermissionCamera.launch(
                            arrayOf(Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
                }
                options[item] == getString(R.string.char_sequence_open_gallery) -> {
                    // Requisitar permissão para acessar galeria de imagens
                    requestPermissionGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                options[item] == getString(R.string.char_sequence_cancel) -> {
                    dialog.dismiss()
                }
            }
        })
        builder.show()
    }


    /**
     * Se houver permissão, acessa a câmera
     */
    private val requestPermissionCamera = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->

        if (resultMap.containsValue(false)) {
            // Permissão Negada
            Toast.makeText(requireContext(), getText(R.string.toast_no_permission_camera), Toast.LENGTH_SHORT).show()
        } else {
            // Permissão Concedida - Cria o arquivo de imagem e chama a câmera
            createFile()
            cameraContent.launch(imageUri)

        }

    }

    /**
     * Se houver permissão, acessa a galeria de imagens
     */
    private val requestPermissionGallery = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted ->

        if (granted) {
            // Permissão Concedida - Executa o ActivityResultContract galleryContent
            galleryContent.launch("image/*")
        } else {
            Toast.makeText(requireContext(), getText(R.string.toast_no_permission_gallery), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Cria uma imagem uri
     */
    private fun createFile() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Nova foto")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem capturada pela camera")
        imageUri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    }
}