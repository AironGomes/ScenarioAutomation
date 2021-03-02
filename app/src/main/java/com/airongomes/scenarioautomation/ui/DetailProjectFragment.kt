package com.airongomes.scenarioautomation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.databinding.FragmentDetailProjectBinding
import com.airongomes.scenarioautomation.viewModel.DetailProjectViewModel
import com.airongomes.scenarioautomation.viewModel.DetailProjectViewModelFactory
import com.airongomes.scenarioautomation.viewModel.HomeViewModelFactory

class DetailProjectFragment : Fragment() {

    lateinit var viewModel: DetailProjectViewModel
    lateinit var arguments: DetailProjectFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailProjectBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_project,
            container,
            false
        )

        // Cria uma instância de database e adiciona o projectDao para viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = ProjectDatabase.getInstance(application).projectDao
        arguments = DetailProjectFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = DetailProjectViewModelFactory(dataSource, arguments.projectId)

        // Cria instância do DetailProjectViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailProjectViewModel::class.java)

        binding.viewModel = viewModel
        // ClickListener para botão fab_new_environment
        binding.fabNewEnvironment.setOnClickListener {
            callNewEnvironmentFragment()
        }

        // Observar o Livedata closeFragment
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                callHomeFragment()
                viewModel.closeFragmentObserved()
            }
        })
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_edit -> {
                this.findNavController().navigate(DetailProjectFragmentDirections.actionDetailProjectFragmentToNewProjectFragment(arguments.projectId))
                true
            }
            R.id.menu_delete -> {
                alertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Cria um Alert Dialog com mensagem de confirmação de exclusão do projeto
     */
    private fun alertDialog() {
        // Cria um Alert Dialog
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.msg_confirm_delete_project))
        builder.setPositiveButton(getString(R.string.button_yes),
                DialogInterface.OnClickListener { dialog, id ->
                    // Exclui projeto do banco de dados
                    viewModel.deleteProject()
                })
        builder.setNegativeButton(R.string.button_cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    Log.i("Log", "Cancel button clicked")
                })
        builder.create().show()
    }

    /**
     * Chamar Fragmento: HomeFragment
     */
    private fun callHomeFragment() {
        this.findNavController().navigate(DetailProjectFragmentDirections.actionDetailProjectFragmentToHomeFragment())
    }

    /**
     * Chamar Fragmento: NewEnvironmentFragment
     */
    private fun callNewEnvironmentFragment() {
        this.findNavController().navigate(DetailProjectFragmentDirections.actionDetailProjectFragmentToNewEnvironmentFragment())
    }

    /**
     * Chamar Fragmento: NewProjectFragment
     */
    private fun callDetailEnvironmentFragment() {
        this.findNavController().navigate(DetailProjectFragmentDirections.actionDetailProjectFragmentToDetailEnvironmentFragment())
    }
}