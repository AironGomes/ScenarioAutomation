package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.DeviceDao
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.ProjectDatabase

/**
 * Entrega o DeviceDao para o ViewModel
 */
class NewDeviceViewModelFactory(
    private val application: Application,
    private val environmentId: Long,
    private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewDeviceViewModel::class.java)) {
            return NewDeviceViewModel(application, environmentId, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}