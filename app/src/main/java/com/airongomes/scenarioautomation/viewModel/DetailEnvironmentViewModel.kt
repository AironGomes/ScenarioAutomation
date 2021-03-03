package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.database.DeviceDao
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.Project
import kotlinx.coroutines.launch

class DetailEnvironmentViewModel(
    environmentSource: EnvironmentDao,
    deviceSource: DeviceDao,
    val environmentId: Long): ViewModel() {

    private val databaseEnvironment = environmentSource
    private val databaseDevice = deviceSource

    // LiveData do ambiente
    val environment: LiveData<Environment> = databaseEnvironment.getEnvironment(environmentId)

    // LiveData de Lista de dispositivos
    val deviceList = databaseDevice.getDeviceList(environmentId)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Exclui projeto do banco de dados
     */
    fun deleteProject() {
        viewModelScope.launch {
            databaseEnvironment.deleteEnvironment(environmentId)
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