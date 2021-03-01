package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao
import com.airongomes.scenarioautomation.utils.ProjectType
import kotlinx.coroutines.launch

class NewProjectViewModel(dataSource: ProjectDao) : ViewModel() {

    private val database = dataSource

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Responsável por Salvar os dados do projeto no banco de dados
     */
    fun saveProject(projectName: String, userName: String, address: String, typeId: Int) {
        // Pega a data atual em milissegundos
        val date = System.currentTimeMillis()

        // Define o tipo do projeto
        val typeEnum: ProjectType = when(typeId) {
            R.id.type_house -> ProjectType.HOME
            R.id.type_store -> ProjectType.STORE
            else -> ProjectType.BUILDING
        }

        // Cria a entidade do banco de dados
        val project = Project(
                projectName = projectName,
                userName = userName,
                address = address,
                type = typeEnum,
                date = date)

        // Insere a entidade no banco de dados usando corountine
        viewModelScope.launch {
            database.insertProject(project)
            _closeFragment.value = true
        }
    }

    /**
     * Reseta o Livedata closeFragment
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }

}