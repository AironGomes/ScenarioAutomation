package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao
import kotlinx.coroutines.launch

class NewProjectViewModel(dataSource: ProjectDao) : ViewModel() {

    private val database = dataSource

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Responsável por Salvar os dados do projeto no banco de dados
     */
    fun saveProject(projectName: String, userName: String, address: String) {
        val date = System.currentTimeMillis()
        val project = Project(
            projectName = projectName,
            userName = userName,
            address = address,
            date = date)

        viewModelScope.launch {
            database.insertProject(project)
            _closeFragment.value = true
        }
    }

    /**
     * Reseta o Livedata closeFragment
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }

}