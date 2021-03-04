package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.ProjectDao
import com.airongomes.scenarioautomation.database.ProjectDatabase

/**
 * Entrega o EnvironmentDao para o ViewModel
 */
class NewEnvironmentViewModelFactory(
    private val application: Application,
    private val environmentId: Long,
    private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewEnvironmentViewModel::class.java)) {
            return NewEnvironmentViewModel(application, environmentId, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}