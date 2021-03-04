package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.repository.Repository
import kotlinx.coroutines.launch

class NewEnvironmentViewModel(
    application: Application,
    val environmentId: Long,
    val projectId: Long): AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // LiveData do ambiente
    var environment: LiveData<Environment>? = null

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Inicializa a livedata environment se existir o ambiente salvo no banco de dados
     */
    init {
        if (environmentId != -1L) {
            environment = repository.getEnvironment(environmentId)
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
                repository.insertEnvironment(environmentData)
            }
        } else {
            val environmentData = Environment(
                    environmentId = environment!!.value!!.environmentId,
                    environmentName = environmentName,
                    imageUri = imageUriString,
                    projectId = projectId)

            viewModelScope.launch {
                repository.updateEnvironment(environmentData)
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