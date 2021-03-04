package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.ProjectDao
import com.airongomes.scenarioautomation.database.ProjectDatabase

/**
 * Entrega o ProjectDao para o ViewModel
 */
class NewProjectViewModelFactory(
        private val application: Application,
        private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewProjectViewModel::class.java)) {
            return NewProjectViewModel(application, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}