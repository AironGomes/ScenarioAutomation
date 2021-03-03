package com.airongomes.scenarioautomation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao
import kotlinx.coroutines.launch

class DetailProjectViewModel(
    projectSource: ProjectDao,
    environmentSource: EnvironmentDao,
    val projectId: Long): ViewModel() {

    private val databaseProject = projectSource
    private val databaseEnvironment = environmentSource

    // LiveData do projeto
    val project: LiveData<Project> = databaseProject.getProject(projectId)

    // LiveData de Lista de Ambientes
    val environmentList = databaseEnvironment.getEnvironmentList(projectId)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Exclui projeto do banco de dados
     */
    fun deleteProject() {
        viewModelScope.launch {
            databaseProject.deleteProject(projectId)
        }
        _closeFragment.value = true
    }

    /**
     * Reseta o Livedata closeFragment
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }
}