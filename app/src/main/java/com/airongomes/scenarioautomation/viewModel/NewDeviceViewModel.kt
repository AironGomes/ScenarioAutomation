package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.database.Device
import com.airongomes.scenarioautomation.repository.Repository
import kotlinx.coroutines.launch

class NewDeviceViewModel(
    application: Application,
    val environmentId: Long,
    val projectId: Long): AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Responsável por Salvar os dados do dispositivo no banco de dados
     */
    fun saveDevice(manufacturer: String, device: String) {
        val deviceData = Device(
                manufacturerName = manufacturer,
                deviceName = device,
                environmentId = environmentId)

        viewModelScope.launch {
            repository.insertDevice(deviceData)
        }

        // Atualiza data e hora de modificação do projeto
        updateDateTime()

        _closeFragment.value = true
    }

    /**
     * Atualiza data e hora de modificação do projeto
     */
    fun updateDateTime() {
        // Atualiza a data do projeto
        val dateTime = System.currentTimeMillis() // Pega a data atual em milissegundos

        viewModelScope.launch {
            repository.updateDateTime(projectId, dateTime)
        }
    }

    /**
     * Reseta o Livedata closeFragment
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }

}