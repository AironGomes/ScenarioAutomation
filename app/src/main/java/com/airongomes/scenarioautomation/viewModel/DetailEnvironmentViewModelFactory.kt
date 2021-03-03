package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.DeviceDao
import com.airongomes.scenarioautomation.database.EnvironmentDao

/**
 * Entrega o DeviceDao para o ViewModel
 */
class DetailEnvironmentViewModelFactory(
    private val environmentSource: EnvironmentDao,
    private val deviceSource: DeviceDao,
    private val environmentId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailEnvironmentViewModel::class.java)) {
            return DetailEnvironmentViewModel(environmentSource, deviceSource, environmentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}