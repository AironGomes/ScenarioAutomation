package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.repository.Repository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    // Instância do repositório
    private val repository = Repository(application)

    // Livedata com lista de projetos
    val projectList = repository.getProjectList()

}