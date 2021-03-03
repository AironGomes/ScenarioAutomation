package com.airongomes.scenarioautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/** DAO para Environment */
@Dao
interface EnvironmentDao {

    @Insert
    suspend fun insertEnvironment(environment: Environment)

    @Update
    suspend fun updateEnvironment(environment: Environment)

    @Query("SELECT * from environment_list_table WHERE environmentId = :environmentId")
    fun getEnvironment(environmentId: Long): LiveData<Environment>

    @Query("SELECT * from environment_list_table WHERE projectId = :projectId")
    fun getEnvironmentList(projectId: Long): LiveData<List<Environment>>

    @Query("DELETE from environment_list_table WHERE environmentId = :environmentId")
    suspend fun deleteEnvironment(environmentId: Long)
}