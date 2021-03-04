package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.database.*
import com.airongomes.scenarioautomation.repository.Repository
import kotlinx.coroutines.launch

class DetailProjectViewModel(
    application: Application,
    val projectId: Long): AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // LiveData do projeto
    val project: LiveData<Project> = repository.getProject(projectId)

    // LiveData de Lista de Ambientes
    val environmentList = repository.getEnvironmentList(projectId)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Exclui projeto do banco de dados
     */
    fun deleteProject() {
        viewModelScope.launch {
            repository.deleteProject(projectId)
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