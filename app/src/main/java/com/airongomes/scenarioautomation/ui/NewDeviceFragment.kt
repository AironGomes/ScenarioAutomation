package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.databinding.FragmentNewDeviceBinding
import com.airongomes.scenarioautomation.databinding.FragmentNewEnvironmentBinding
import com.airongomes.scenarioautomation.viewModel.NewDeviceViewModel
import com.airongomes.scenarioautomation.viewModel.NewDeviceViewModelFactory
import com.airongomes.scenarioautomation.viewModel.NewEnvironmentViewModel
import com.airongomes.scenarioautomation.viewModel.NewEnvironmentViewModelFactory

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
        val dataSource = ProjectDatabase.getInstance(application).deviceDao
        arguments = NewDeviceFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = NewDeviceViewModelFactory(dataSource, arguments.environmentId)

        // Cria instância do DetailProjectViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewDeviceViewModel::class.java)

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
        val deviceName = binding.deviceEditText.text.toString()

        if(deviceName.isBlank()) {
            Toast.makeText(
                requireContext(),
                getText(R.string.message_device_name_is_blank),
                Toast.LENGTH_LONG
            ).show()
        }
        else viewModel.saveDevice(deviceName)
    }

    /**
     * Chamar Fragmento: DetailEnvironmentFragment
     */
    private fun callDetailEnvironmentFragment(environmentId: Long) {
        this.findNavController().navigate(NewDeviceFragmentDirections.actionNewDeviceFragmentToDetailEnvironmentFragment(environmentId))
    }
}