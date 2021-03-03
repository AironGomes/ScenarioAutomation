package com.airongomes.scenarioautomation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.databinding.ItemEnvironmentBinding

class EnvironmentAdapter(var environmentList: List<Environment> = listOf(),
                          private val clickListener: EnvironmentClickListener):
    RecyclerView.Adapter<EnvironmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnvironmentViewHolder {
        return EnvironmentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EnvironmentViewHolder, position: Int) {
        holder.bind(environmentList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return environmentList.size
    }
}

class EnvironmentViewHolder private constructor(val binding: ItemEnvironmentBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(environment: Environment, clickListener: EnvironmentClickListener){
        binding.environment = environment
        binding.executePendingBindings()
        binding.clickListener = clickListener
    }

    companion object {
        fun from(parent: ViewGroup) : EnvironmentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemEnvironmentBinding.inflate(layoutInflater, parent, false)
            return EnvironmentViewHolder(binding)
        }
    }
}

/**
 * Classe criada para associar um ClickListener aos itens do recyclerview
 */
class EnvironmentClickListener(val clickListener: (environmentId: Long) -> Unit) {

    fun onClick(environment: Environment) = clickListener(environment.environmentId)

}