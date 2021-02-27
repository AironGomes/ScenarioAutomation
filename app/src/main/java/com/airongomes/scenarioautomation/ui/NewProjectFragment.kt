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
import com.airongomes.scenarioautomation.databinding.FragmentNewProjectBinding
import com.airongomes.scenarioautomation.viewModel.NewProjectViewModel

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

        // Criando instância de viewModel
        viewModel = ViewModelProvider(this).get(NewProjectViewModel::class.java)


        // Observar closeFragment LiveData
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                callHomeFragment()
                viewModel.closeFragmentObserved() // Resetar LiveData
            }
        })

        // ClickListener para botão Confirmar
        binding.buttonConfirm.setOnClickListener { confirmProjectData() }

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
    private fun confirmProjectData() {
        if(binding.projectEditText.text.isNullOrBlank() ||
            binding.userEditText.text.isNullOrBlank()) {
            Toast.makeText(
                requireContext(),
                getText(R.string.message_project_name_or_user_is_blank),
                Toast.LENGTH_LONG
            ).show()
        }
        else viewModel.saveProject()
    }
}