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
import com.airongomes.scenarioautomation.databinding.FragmentNewProjectBinding
import com.airongomes.scenarioautomation.viewModel.NewProjectViewModel
import com.airongomes.scenarioautomation.viewModel.NewProjectViewModelFactory

class NewProjectFragment : Fragment() {

    private lateinit var binding: FragmentNewProjectBinding
    private lateinit var viewModel: NewProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_project,
            container,
            false)

        // Cria uma instância de database e adiciona o projectDao para viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = ProjectDatabase.getInstance(application).projectDao
        val viewModelFactory = NewProjectViewModelFactory(dataSource)

        // Cria instância de HomeViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewProjectViewModel::class.java)

        // ClickListener para botão Cancelar
        binding.buttonCancel.setOnClickListener { callHomeFragment() }

        // ClickListener para botão Confirmar
        binding.buttonConfirm.setOnClickListener { saveProject() }

        // Observar o Livedata closeFragment
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                callHomeFragment()
                viewModel.closeFragmentObserved()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    /**
     * Chamar Fragmento: HomeFragment
     */
    private fun callHomeFragment() {
        this.findNavController().navigate(NewProjectFragmentDirections.actionNewProjectFragmentToHomeFragment())
    }

    /**
     * Verifica se os campos estão preenchidos e chama saveProject de ViewModel
     */
    private fun saveProject() {
        val projectName = binding.projectEditText.text.toString()
        val userName = binding.userEditText.text.toString()
        val address = binding.addressEditText.text.toString()
        val type = binding.radioGroup.checkedRadioButtonId

        if(projectName.isBlank() || userName.isBlank()) {
            Toast.makeText(
                requireContext(),
                getText(R.string.message_project_name_or_user_is_blank),
                Toast.LENGTH_LONG
            ).show()
        }
        else viewModel.saveProject(projectName, userName, address, type)
    }
}