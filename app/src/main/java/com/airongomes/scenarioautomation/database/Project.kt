package com.airongomes.scenarioautomation.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_list_table")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val projectId: Long = 0L,
    val projectName: String = "",
    val userName: String = "",
    val address: String = "",
    val date: Long = 0L)