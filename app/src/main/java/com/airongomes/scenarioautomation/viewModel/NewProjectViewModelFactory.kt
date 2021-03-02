package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.ProjectDao

/**
 * Entrega o ProjectDao para o ViewModel
 */
class NewProjectViewModelFactory(
        private val dataSource: ProjectDao,
        private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewProjectViewModel::class.java)) {
            return NewProjectViewModel(dataSource, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}