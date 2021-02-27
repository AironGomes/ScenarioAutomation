package com.airongomes.scenarioautomation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewProjectViewModel: ViewModel() {

    // Livedata usado para fechar o fragmento
    private var _closeFragment = MutableLiveData<Boolean>()
    val closeFragment: LiveData<Boolean>
        get() = _closeFragment

    /**
     * onClick buttonCancel de fragment_new_project
     */
    fun cancelButton() {
        _closeFragment.value = true
    }

    /**
     * Responsável por Salvar os dados do projeto no banco de dados
     */
    fun saveProject() {}


    /**
     * Resetar liveData após ser observada
     */
    fun closeFragmentObserved() {
        _closeFragment.value = false
    }

}