package com.airongomes.scenarioautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/** DAO para Projeto */
@Dao
interface ProjectDao {

    @Insert
    suspend fun insertProject(project: Project)

    @Update
    suspend fun updateProject(project: Project)

    @Query("SELECT * from project_list_table WHERE projectId = :projectId")
    fun getProject(projectId: Long): LiveData<Project>

    @Query("SELECT * from project_list_table ")
    fun getProjectList(): LiveData<List<Project>>

    @Query("DELETE from project_list_table WHERE projectId = :projectId")
    suspend fun deleteProject(projectId: Long)

    @Query("UPDATE project_list_table SET date = :dateTime WHERE projectId = :projectId")
    suspend fun updateDateTime(projectId: Long, dateTime: Long)

}
