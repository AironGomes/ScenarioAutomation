package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.ProjectDao

/**
 * Entrega o EnvironmentDao para o ViewModel
 */
class NewEnvironmentViewModelFactory(
    private val dataSource: EnvironmentDao,
    private val environmentId: Long,
    private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewEnvironmentViewModel::class.java)) {
            return NewEnvironmentViewModel(dataSource, environmentId, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}