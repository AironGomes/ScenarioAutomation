package com.airongomes.scenarioautomation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.databinding.ItemProjectBinding

class RecyclerViewAdapter(var projectList: List<Project> = listOf()): RecyclerView.Adapter<AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(projectList[position])
    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}

class AdapterViewHolder private constructor(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Project){
        binding.project = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup) : AdapterViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProjectBinding.inflate(layoutInflater, parent, false)
            return AdapterViewHolder(binding)
        }
    }
}