package com.airongomes.scenarioautomation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.databinding.ItemProjectBinding

class ProjectAdapter(var projectList: List<Project> = listOf(),
                     private val clickListener: ProjectClickListener): RecyclerView.Adapter<ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(projectList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}

class ProjectViewHolder private constructor(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(project: Project, clickListener: ProjectClickListener){
        binding.project = project
        binding.executePendingBindings()
        binding.clickListener = clickListener
    }

    companion object {
        fun from(parent: ViewGroup) : ProjectViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProjectBinding.inflate(layoutInflater, parent, false)
            return ProjectViewHolder(binding)
        }
    }
}

/**
 * Classe criada para associar um ClickListener aos itens do recyclerview
 */
class ProjectClickListener(val clickListener: (projectId: Long) -> Unit) {

    fun onClick(project: Project) = clickListener(project.projectId)

}