package com.airongomes.scenarioautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentDetailEnvironmentBinding

class DetailEnvironmentFragment: Fragment() {

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

        // ClickListener para botão fab_new_device
        binding.fabNewDevice.setOnClickListener {
            callNewDeviceFragment()
        }

        return binding.root
    }

    /**
     * Chamar Fragmento: NewDeviceFragment
     */
    private fun callNewDeviceFragment() {
        this.findNavController().navigate(DetailEnvironmentFragmentDirections.actionDetailEnvironmentFragmentToNewDeviceFragment())
    }

}