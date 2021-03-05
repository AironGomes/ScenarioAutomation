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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.adapter.*
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.databinding.FragmentDetailEnvironmentBinding
import com.airongomes.scenarioautomation.viewModel.DetailEnvironmentViewModel
import com.airongomes.scenarioautomation.viewModel.DetailEnvironmentViewModelFactory
import com.airongomes.scenarioautomation.viewModel.DetailProjectViewModel
import com.airongomes.scenarioautomation.viewModel.DetailProjectViewModelFactory

class DetailEnvironmentFragment: Fragment() {

    lateinit var viewModel: DetailEnvironmentViewModel
    lateinit var arguments: DetailEnvironmentFragmentArgs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailEnvironmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_environment,
            container,
            false
        )

        // Cria uma instância de database e adiciona o projectDao para viewModel
        val application = requireNotNull(this.activity).application
        arguments = DetailEnvironmentFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = DetailEnvironmentViewModelFactory(application, arguments.environmentId)

        // Cria instância do DetailProjectViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailEnvironmentViewModel::class.java)

        binding.viewModel = viewModel

        // Cria instância de EnvironmentAdapter
        val adapter = DeviceAdapter(listOf(), DeviceClickListener { deviceId ->
            viewModel.deleteDevice(deviceId)
        })

        // Adiciona adapter para o RecyclerView
        binding.recyclerViewDevices.adapter = adapter

        // Cria um layout manager
        val layoutManager = LinearLayoutManager(activity)
        // Associa o recyclerview com o layout manager
        binding.recyclerViewDevices.layoutManager = layoutManager
        // Cria um item decoration do recyclerview (Divider)
        val mDividerItemDecoration = DividerItemDecoration(
            binding.recyclerViewDevices.context,
            layoutManager.orientation
        )
        // Associa o recyclerview com o item decoration
        binding.recyclerViewDevices.addItemDecoration(mDividerItemDecoration)


        viewModel.deviceList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.deviceList = it
                adapter.notifyDataSetChanged()
                binding.viewGroupNoDevice.visibility = View.GONE
            }

            if (it.isNullOrEmpty()) {
                binding.viewGroupNoDevice.visibility = View.VISIBLE
            }
        })


        // ClickListener para botão fab_new_device
        binding.fabNewDevice.setOnClickListener {
            callNewDeviceFragment(arguments.environmentId)
        }

        // ClickListener para viewGroupNoProject
        binding.viewGroupNoDevice.setOnClickListener {
            callNewDeviceFragment(arguments.environmentId)
        }

        // Observar o Livedata closeFragment
        viewModel.closeFragment.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                callDetailProjectFragment(viewModel.environment.value!!.projectId)
                viewModel.closeFragmentObserved()
            }
        })

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details_environment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_edit_environment -> {
                callNewEnvironmentFragment(
                    environmentId = arguments.environmentId,
                    projectId = viewModel.environment.value!!.projectId
                )
                true
            }
            R.id.menu_delete_environment -> {
                alertDialogDeleteEnvironment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Cria um Alert Dialog com mensagem de confirmação de exclusão do ambiente
     */
    private fun alertDialogDeleteEnvironment() {
        // Cria um Alert Dialog
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.msg_confirm_delete_environment))
        builder.setPositiveButton(getString(R.string.button_yes),
            DialogInterface.OnClickListener { dialog, id ->
                // Exclui o ambiente atual
                viewModel.deleteEnvironment()
            })
        builder.setNegativeButton(R.string.button_cancel,
            DialogInterface.OnClickListener { dialog, id ->
                Log.i("Log", "Cancel button clicked")
            })
        builder.create().show()
    }

    /**
     * Chamar Fragmento: NewDeviceFragment
     */
    private fun callDetailProjectFragment(projectId: Long) {
        this.findNavController().navigate(DetailEnvironmentFragmentDirections
            .actionDetailEnvironmentFragmentToDetailProjectFragment(projectId))
    }

    /**
     * Chamar Fragmento: NewDeviceFragment
     */
    private fun callNewDeviceFragment(environmentId: Long) {
        this.findNavController().navigate(DetailEnvironmentFragmentDirections
            .actionDetailEnvironmentFragmentToNewDeviceFragment(environmentId, viewModel.environment.value!!.projectId))
    }

    /**
     * Chamar Fragmento: NewEnvironmentFragment
     */
    private fun callNewEnvironmentFragment(environmentId: Long, projectId: Long) {
        this.findNavController().navigate(DetailEnvironmentFragmentDirections
            .actionDetailEnvironmentFragmentToNewEnvironmentFragment(environmentId, projectId))
    }

}