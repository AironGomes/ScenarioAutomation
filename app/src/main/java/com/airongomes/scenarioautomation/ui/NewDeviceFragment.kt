package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentNewDeviceBinding

class NewDeviceFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewDeviceBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_new_device,
            container,
            false)

        // ClickListener para botão button_confirm
        binding.buttonConfirm.setOnClickListener {
            callDetailEnvironmentFragment()
        }

        // ClickListener para botão button_cancel
        binding.buttonCancel.setOnClickListener {
            callDetailEnvironmentFragment()
        }

        return binding.root
    }

    /**
     * Chamar Fragmento: DetailEnvironmentFragment
     */
    private fun callDetailEnvironmentFragment() {
        this.findNavController().navigate(NewDeviceFragmentDirections.actionNewDeviceFragmentToDetailEnvironmentFragment())
    }
}