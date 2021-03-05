package com.airongomes.scenarioautomation.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.EnvironmentDao
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDatabase
import com.airongomes.scenarioautomation.repository.Repository
import kotlinx.coroutines.launch
import java.net.URI

class NewEnvironmentViewModel(
    application: Application,
    val environmentId: Long,
    val projectId: Long): AndroidViewModel(application) {

    // Instância do repositório
    private val repository = Repository(application)

    // LiveData do ambiente
    var environment: LiveData<Environment>? = null

    // Livedata para fechar o fragmento
    private val _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    // Livedata Uri da imagem
    private val _uriImage = MutableLiveData<Uri>()
    val uriImage: LiveData<Uri>
        get() = _uriImage

    /**
     * Inicializa a livedata environment se existir o ambiente salvo no banco de dados
     */
    init {
        if (environmentId != -1L) {
            environment = repository.getEnvironment(environmentId)
        }
    }

    /**
    * Responsável por Salvar os dados do ambiente no banco de dados
    */
    fun saveEnvironment(environmentName: String) {

        val newUriImage = uriImage.value
        val newUriImageString = newUriImage?.toString()
        if(environment == null) {
            val environmentData = Environment(
                    environmentName = environmentName,
                    imageUri = newUriImageString,
                    projectId = projectId)

            viewModelScope.launch {
                repository.insertEnvironment(environmentData)
            }
        } else {
            val environmentData = Environment(
                    environmentId = environment!!.value!!.environmentId,
                    environmentName = environmentName,
                    imageUri = newUriImageString,
                    projectId = projectId)

            viewModelScope.launch {
                repository.updateEnvironment(environmentData)
            }
        }

        // Atualiza data e hora de modificação do projeto
        updateDateTime()

        _closeFragment.value = true
    }

    /**
     * Atualiza data e hora de modificação do projeto
     */
    fun updateDateTime() {
        // Atualiza a data do projeto
        val dateTime = System.currentTimeMillis() // Pega a data atual em milissegundos

        viewModelScope.launch {
            repository.updateDateTime(projectId, dateTime)
        }

    }

    /**
     * Reseta o Livedata closeFragment
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }

    /**
     * Definir image_uri
     */
    fun setImageUri(uri: Uri) {
        _uriImage.value = uri
    }
}