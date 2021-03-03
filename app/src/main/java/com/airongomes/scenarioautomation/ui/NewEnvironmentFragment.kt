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
import com.airongomes.scenarioautomation.databinding.FragmentNewEnvironmentBinding
import com.airongomes.scenarioautomation.viewModel.DetailProjectViewModel
import com.airongomes.scenarioautomation.viewModel.DetailProjectViewModelFactory
import com.airongomes.scenarioautomation.viewModel.NewEnvironmentViewModel
import com.airongomes.scenarioautomation.viewModel.NewEnvironmentViewModelFactory

class NewEnvironmentFragment: Fragment() {

    lateinit var binding: FragmentNewEnvironmentBinding
    lateinit var viewModel: NewEnvironmentViewModel
    lateinit var arguments: NewEnvironmentFragmentArgs

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
        val dataSource = ProjectDatabase.getInstance(application).environmentDao
        arguments = NewEnvironmentFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = NewEnvironmentViewModelFactory(dataSource, arguments.projectId)

        // Cria instância do DetailProjectViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewEnvironmentViewModel::class.java)

        // ClickListener para botão button_confirm
        binding.buttonConfirm.setOnClickListener { saveEnvironment() }

        // ClickListener para botão button_cancel
        binding.buttonCancel.setOnClickListener { callDetailProjectFragment(arguments.projectId) }

        // Observar o Livedata closeFragment
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                callDetailProjectFragment(arguments.projectId)
                viewModel.closeFragmentObserved()
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
}