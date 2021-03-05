package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentNewDeviceBinding
import com.airongomes.scenarioautomation.viewModel.NewDeviceViewModel
import com.airongomes.scenarioautomation.viewModel.NewDeviceViewModelFactory
import com.google.android.material.snackbar.Snackbar

class NewDeviceFragment: Fragment() {

    lateinit var binding: FragmentNewDeviceBinding
    lateinit var viewModel: NewDeviceViewModel
    lateinit var arguments: NewDeviceFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,
            R.layout.fragment_new_device,
            container,
            false)

        // Cria uma instância de database e adiciona o projectDao para viewModel
        val application = requireNotNull(this.activity).application
        arguments = NewDeviceFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = NewDeviceViewModelFactory(application, arguments.environmentId, arguments.projectId)

        // Cria instância do DetailProjectViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewDeviceViewModel::class.java)

        // Spinner para opções
        val spinner: Spinner = binding.spinner

        // Array Adapter com string Array
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.options_device,
                android.R.layout.simple_gallery_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // Se o dispositivo não for da Scenario, mostra a tela para inserir dados do dispositivo
        binding.switchScenario.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.spinner.visibility = View.VISIBLE
                binding.manufacturerTextField.visibility = View.GONE
                binding.deviceTextField.visibility = View.GONE
            }
            else {
                binding.spinner.visibility = View.GONE
                binding.manufacturerTextField.visibility = View.VISIBLE
                binding.deviceTextField.visibility = View.VISIBLE
            }
        }

        // ClickListener para botão button_confirm
        binding.buttonConfirm.setOnClickListener {
            saveDevice()
        }

        // ClickListener para botão button_cancel
        binding.buttonCancel.setOnClickListener {
            callDetailEnvironmentFragment(arguments.environmentId)
        }

        // Observar o Livedata closeFragment
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                        binding.viewGroupId,
                        resources.getText(R.string.message_saved),
                        Snackbar.LENGTH_SHORT
                ).show()
                callDetailEnvironmentFragment(arguments.environmentId)
                viewModel.closeFragmentObserved()
            }
        })

        return binding.root
    }

    /**
     * Verifica se os campos estão preenchidos e chama saveEnvironment de ViewModel
     */
    private fun saveDevice() {
        if (binding.switchScenario.isChecked) {
            val manufacturer = "Scenario Automation"
            val device = binding.spinner.selectedItem.toString()
            viewModel.saveDevice(manufacturer, device)
        }
        else {
            val device = binding.deviceEditText.text.toString()
            val manufacturer = binding.manufacturerEditText.text.toString()
            if(device.isBlank() || manufacturer.isBlank()) {
                Toast.makeText(
                        requireContext(),
                        getText(R.string.message_device_name_is_blank),
                        Toast.LENGTH_LONG
                ).show()
            }
            else {
                viewModel.saveDevice(manufacturer, device)
            }
        }

    }

    /**
     * Chamar Fragmento: DetailEnvironmentFragment
     */
    private fun callDetailEnvironmentFragment(environmentId: Long) {
        this.findNavController().navigate(NewDeviceFragmentDirections.actionNewDeviceFragmentToDetailEnvironmentFragment(environmentId))
    }

}