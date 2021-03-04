package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airongomes.scenarioautomation.database.ProjectDatabase

/**
 * Entrega o ProjectDao para o ViewModel
 */
class HomeViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}