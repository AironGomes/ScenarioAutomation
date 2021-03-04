package com.airongomes.scenarioautomation.repository

import android.app.Application
import com.airongomes.scenarioautomation.database.Device
import com.airongomes.scenarioautomation.database.Environment
import com.airongomes.scenarioautomation.database.Project
import com.airongomes.scenarioautomation.database.ProjectDatabase

/**
 * Repositório do Projeto com todas as interações com o banco de dados
 */
class Repository(application: Application) {

    // Instância de database
    val projectDatabase = ProjectDatabase.getInstance(application)

    /* ------------------------------- ProjectDao -----------------------------------------*/

    /**
     * Insere projeto no banco de dados
     */
    suspend fun insertProject(project: Project) = projectDatabase.projectDao.insertProject(project)

    /**
     * Atualiza projeto no banco de dados
     */
    suspend fun updateProject(project: Project) = projectDatabase.projectDao.updateProject(project)

    /**
     * Exclui projeto do banco de dados
     */
    suspend fun deleteProject(projectId: Long) = projectDatabase.projectDao.deleteProject(projectId)

    /**
     * Retorna um livedata do projeto
     */
    fun getProject(projectId: Long) = projectDatabase.projectDao.getProject(projectId)

    /**
     * Retorna uma lista de livedata dos projetos
     */
    fun getProjectList() = projectDatabase.projectDao.getProjectList()



    /* ------------------------------- EnvironmentDao -----------------------------------------*/

    /**
     * Insere ambiente no banco de dados
     */
    suspend fun insertEnvironment(environment: Environment) = projectDatabase.environmentDao.insertEnvironment(environment)

    /**
     * Atualiza ambiente no banco de dados
     */
    suspend fun updateEnvironment(environment: Environment) = projectDatabase.environmentDao.updateEnvironment(environment)

    /**
     * Exclui ambiente do banco de dados
     */
    suspend fun deleteEnvironment(environmentId: Long) = projectDatabase.environmentDao.deleteEnvironment(environmentId)

    /**
     * Retorna um livedata do ambiente
     */
    fun getEnvironment(environmentId: Long) = projectDatabase.environmentDao.getEnvironment(environmentId)

    /**
     * Retorna uma lista de livedata dos ambientes
     */
    fun getEnvironmentList(projectId: Long) = projectDatabase.environmentDao.getEnvironmentList(projectId)



    /* ------------------------------- DeviceDao -----------------------------------------*/

    /**
     * Insere dispositivo no banco de dados
     */
    suspend fun insertDevice(device: Device) = projectDatabase.deviceDao.insertDevice(device)

    /**
     * Atualiza dispositivo no banco de dados
     */
    suspend fun updateDevice(device: Device) = projectDatabase.deviceDao.updateDevice(device)

    /**
     * Exclui dispositivo do banco de dados
     */
    suspend fun deleteDevice(deviceId: Long) = projectDatabase.deviceDao.deleteDevice(deviceId)

    /**
     * Retorna um livedata do dispositivo
     */
    fun getDevice(deviceId: Long) = projectDatabase.deviceDao.getDevice(deviceId)

    /**
     * Retorna uma lista de livedata dos dispositivos
     */
    fun getDeviceList(environmentId: Long) = projectDatabase.deviceDao.getDeviceList(environmentId)

}