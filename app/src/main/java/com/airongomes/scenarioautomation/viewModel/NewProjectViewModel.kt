package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDao
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.repository.Repository
import com.airongomes.scenarioautomation.utils.ProjectType
import kotlinx.coroutines.launch

class NewProjectViewModel(
        application: Application,
        projectId: Long) : AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // LiveData of project
    var project: LiveData<Project>? = null


    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * Inicializa a livedata project se existir o projeto salvo no banco de dados
     */
    init {
        if (projectId != -1L) {
            project = repository.getProject(projectId)
        }
    }

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

        if (project == null) {
            // Cria a entidade do banco de dados
            val projectData = Project(
                    projectName = projectName,
                    userName = userName,
                    address = address,
                    type = typeEnum,
                    date = date)

            // Insere a entidade no banco de dados usando corountine
            viewModelScope.launch {
                repository.insertProject(projectData)
            }
        } else {
            // Cria a entidade do banco de dados com o id do projeto recebido
            val projectData = Project(
                    projectId = project!!.value!!.projectId,
                    projectName = projectName,
                    userName = userName,
                    address = address,
                    type = typeEnum,
                    date = date)

            // Atualiza o projeto no banco de dados
            viewModelScope.launch {
                repository.updateProject(projectData)
            }
        }

        _closeFragment.value = true
    }

    /**
     * Reseta o Livedata closeFragment
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }

}