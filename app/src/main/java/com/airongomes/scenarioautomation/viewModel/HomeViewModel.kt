package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao

class HomeViewModel(dataSource: ProjectDao) : ViewModel() {

    private val database = dataSource

    // Livedata com lista de projetos
    val projectList = database.getProjectList()

}