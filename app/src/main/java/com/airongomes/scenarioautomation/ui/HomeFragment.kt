package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.adapter.ProjectClickListener
import com.airongomes.scenarioautomation.adapter.ProjectAdapter
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.databinding.FragmentHomeBinding
import com.airongomes.scenarioautomation.viewModel.HomeViewModel
import com.airongomes.scenarioautomation.viewModel.HomeViewModelFactory

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false)

        // Cria uma instância de database e adiciona o projectDao para viewModel
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(application)

        // Cria instância de HomeViewModel
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // Cria instancia de ProjectAdapter
        val adapter = ProjectAdapter(listOf(), ProjectClickListener{ projectId ->
            callDetailProjectFragment(projectId)
        })

        // Adiciona adapter para o RecyclerView
        binding.recyclerViewProjects.adapter = adapter

        // Cria um layout manager
        val layoutManager = LinearLayoutManager(activity)
        // Associa o recyclerview com o layout manager
        binding.recyclerViewProjects.layoutManager = layoutManager

        viewModel.projectList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.projectList = it
                adapter.notifyDataSetChanged()
                binding.viewGroupNoProject.visibility = View.GONE
            }

            if (it.isNullOrEmpty()) {
                binding.viewGroupNoProject.visibility = View.VISIBLE
            }
        })

        // ClickListener para botão fab_new_project
        binding.fabNewProject.setOnClickListener {
            callNewProjectFragment()
        }

        // ClickListener para viewGroupNoProject
        binding.viewGroupNoProject.setOnClickListener {
            callNewProjectFragment()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_about -> {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAboutFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Chamar Fragmento: NewProjectFragment
     */
    private fun callNewProjectFragment() {
        this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewProjectFragment(-1L))
    }

    /**
     * Chamar Fragmento: DetailProjectFragment
     */
    private fun callDetailProjectFragment(projectId: Long) {
        this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailProjectFragment(projectId))
    }
}