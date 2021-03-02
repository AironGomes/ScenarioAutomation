package com.airongomes.scenarioautomation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao
import kotlinx.coroutines.launch

class DetailProjectViewModel(dataSource: ProjectDao, val projectId: Long): ViewModel() {

    private val database = dataSource

    // LiveData of task
    val project: LiveData<Project> = database.getProject(projectId)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Exclui projeto do banco de dados
     */
    fun deleteProject() {
        viewModelScope.launch {
            database.deleteProject(projectId)
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