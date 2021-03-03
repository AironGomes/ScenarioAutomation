package com.airongomes.scenarioautomation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airongomes.scenarioautomation.database.Device
import com.airongomes.scenarioautomation.databinding.ItemDeviceBinding

class DeviceAdapter(var deviceList: List<Device> = listOf()):
    RecyclerView.Adapter<DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(deviceList[position])
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }
}

class DeviceViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(device: Device){
        binding.device = device
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup) : DeviceViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemDeviceBinding.inflate(layoutInflater, parent, false)
            return DeviceViewHolder(binding)
        }
    }
}

