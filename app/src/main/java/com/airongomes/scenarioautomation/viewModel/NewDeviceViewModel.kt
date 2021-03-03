package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.Device
import com.airongomes.scenarioautomation.database.DeviceDao
import com.airongomes.scenarioautomation.database.Environment
import kotlinx.coroutines.launch

class NewDeviceViewModel(
    dataSource: DeviceDao,
    val environmentId: Long
): ViewModel() {

    private val database = dataSource

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
            database.insertDevice(deviceData)
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