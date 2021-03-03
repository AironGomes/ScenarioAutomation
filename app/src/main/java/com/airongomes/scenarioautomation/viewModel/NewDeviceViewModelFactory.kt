package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.DeviceDao
import com.airongomes.scenarioautomation.database.EnvironmentDao

/**
 * Entrega o DeviceDao para o ViewModel
 */
class NewDeviceViewModelFactory(
    private val dataSource: DeviceDao,
    private val environmentId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewDeviceViewModel::class.java)) {
            return NewDeviceViewModel(dataSource, environmentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}