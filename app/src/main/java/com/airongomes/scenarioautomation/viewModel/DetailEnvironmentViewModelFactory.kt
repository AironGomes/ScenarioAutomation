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
class DetailEnvironmentViewModelFactory(
    private val application: Application,
    private val environmentId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailEnvironmentViewModel::class.java)) {
            return DetailEnvironmentViewModel(application, environmentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}