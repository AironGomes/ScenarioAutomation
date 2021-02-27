package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentHomeBinding

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

        // ClickListener para botão fab_new_project
        binding.fabNewProject.setOnClickListener {
            callNewProjectFragment()
        }

        // ClickListener para botão button_detail_project
        binding.buttonDetailProject.setOnClickListener {
            callDetailProjectFragment()
        }

        return binding.root
    }

    /**
     * Chamar Fragmento: NewProjectFragment
     */
    private fun callNewProjectFragment() {
        this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewProjectFragment())
    }

    /**
     * Chamar Fragmento: DetailProjectFragment
     */
    private fun callDetailProjectFragment() {
        this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailProjectFragment())
    }
}