package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentDetailProjectBinding

class DetailProjectFragment : Fragment() {

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

        // ClickListener para botão fab_new_environment
        binding.fabNewEnvironment.setOnClickListener {
            callNewEnvironmentFragment()
        }

        // ClickListener para botão button_detail_environment
        binding.buttonDetailEnvironment.setOnClickListener {
            callDetailEnvironmentFragment()
        }

        return binding.root
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