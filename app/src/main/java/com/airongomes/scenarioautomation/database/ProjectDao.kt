package com.airongomes.scenarioautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProjectDao {

    @Insert
    suspend fun insertProject(project: Project)

    @Query("SELECT * from project_list_table ")
    fun getProjectList(): LiveData<List<Project>>

    @Query("DELETE from project_list_table WHERE projectId = :projectId")
    fun deleteProject(projectId: Long)
}