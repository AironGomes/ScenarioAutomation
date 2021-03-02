package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentNewEnvironmentBinding

class NewEnvironmentFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewEnvironmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_new_environment,
            container,
            false)

        // ClickListener para botão button_confirm
        binding.buttonConfirm.setOnClickListener {
            //callDetailProjectFragment()
        }

        // ClickListener para botão button_cancel
        binding.buttonCancel.setOnClickListener {
            //callDetailProjectFragment()
        }

        return binding.root
    }

    /**
     * Chamar Fragmento: callDetailProjectFragment
     */
    private fun callDetailProjectFragment(projectId: Long) {
        this.findNavController().navigate(NewEnvironmentFragmentDirections.actionNewEnvironmentFragmentToDetailProjectFragment(projectId))
    }
}