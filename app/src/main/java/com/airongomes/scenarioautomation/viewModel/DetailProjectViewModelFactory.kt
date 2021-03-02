package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.ProjectDao

/**
 * Entrega o ProjectDao para o ViewModel
 */
class DetailProjectViewModelFactory(
        private val dataSource: ProjectDao,
        private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailProjectViewModel::class.java)) {
            return DetailProjectViewModel(dataSource, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}