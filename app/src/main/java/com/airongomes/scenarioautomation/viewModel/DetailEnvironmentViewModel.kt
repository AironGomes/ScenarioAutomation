package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.database.*
import com.airongomes.scenarioautomation.repository.Repository
import kotlinx.coroutines.launch

class DetailEnvironmentViewModel(
    application: Application,
    val environmentId: Long): AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // LiveData do ambiente
    val environment: LiveData<Environment> = repository.getEnvironment(environmentId)

    // LiveData de Lista de dispositivos
    val deviceList = repository.getDeviceList(environmentId)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Exclui ambiente do banco de dados
     */
    fun deleteEnvironment() {
        viewModelScope.launch {
            repository.deleteEnvironment(environmentId)
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