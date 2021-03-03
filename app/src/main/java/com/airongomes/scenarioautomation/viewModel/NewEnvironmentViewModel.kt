package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.EnvironmentDao
import kotlinx.coroutines.launch

class NewEnvironmentViewModel(
    dataSource: EnvironmentDao,
    val projectId: Long): ViewModel() {

    private val database = dataSource

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment


    /**
    * Responsável por Salvar os dados do ambiente no banco de dados
    */
    fun saveEnvironment(environmentName: String) {
        // Pega a data atual em milissegundos
        val date = System.currentTimeMillis() // TODO: ATUALIZAR DATA

        val environmentData = Environment(
            environmentName = environmentName,
            projectId = projectId)

        viewModelScope.launch {
            database.insertEnvironment(environmentData)
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