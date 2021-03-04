package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.Project
import kotlinx.coroutines.launch

class NewEnvironmentViewModel(
    dataSource: EnvironmentDao,
    val environmentId: Long,
    val projectId: Long): ViewModel() {

    private val database = dataSource

    // LiveData do ambiente
    var environment: LiveData<Environment>? = null

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Inicializa a livedata project se existir o projeto salvo no banco de dados
     */
    init {
        if (environmentId != -1L) {
            environment = database.getEnvironment(environmentId)
        }
    }

    /**
    * Responsável por Salvar os dados do ambiente no banco de dados
    */
    fun saveEnvironment(environmentName: String, imageUriString: String?) {
        // Pega a data atual em milissegundos
        val date = System.currentTimeMillis() // TODO: ATUALIZAR DATA

        if(environment == null) {
            val environmentData = Environment(
                    environmentName = environmentName,
                    imageUri = imageUriString,
                    projectId = projectId)

            viewModelScope.launch {
                database.insertEnvironment(environmentData)
            }
        } else {
            val environmentData = Environment(
                    environmentId = environment!!.value!!.environmentId,
                    environmentName = environmentName,
                    imageUri = imageUriString,
                    projectId = projectId)

            viewModelScope.launch {
                database.updateEnvironment(environmentData)
            }
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