package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.ProjectDao
import com.airongomes.scenarioautomation.database.ProjectDatabase

/**
 * Entrega o ProjectDao para o ViewModel
 */
class DetailProjectViewModelFactory(
    private val application: Application,
    private val projectId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailProjectViewModel::class.java)) {
            return DetailProjectViewModel(application, projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}