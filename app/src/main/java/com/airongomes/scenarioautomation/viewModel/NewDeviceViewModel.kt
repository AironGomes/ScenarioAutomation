package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.database.Device
import com.airongomes.scenarioautomation.database.DeviceDao
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.repository.Repository
import kotlinx.coroutines.launch

class NewDeviceViewModel(
    application: Application,
    val environmentId: Long): AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment


    /**
     * Responsável por Salvar os dados do dispositivo no banco de dados
     */
    fun saveDevice(deviceName: String) {
        // Pega a data atual em milissegundos
        val date = System.currentTimeMillis() // TODO: ATUALIZAR DATA

        val deviceData = Device(
            deviceName = deviceName,
            environmentId = environmentId)

        viewModelScope.launch {
            repository.insertDevice(deviceData)
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