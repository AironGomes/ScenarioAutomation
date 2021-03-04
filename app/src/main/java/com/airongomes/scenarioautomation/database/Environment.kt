package com.airongomes.scenarioautomation.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "environment_list_table", foreignKeys = [ForeignKey(entity = Project::class,
        parentColumns = arrayOf("projectId"),
        childColumns = arrayOf("projectId"),
        onDelete = ForeignKey.CASCADE)])

data class Environment(
        @PrimaryKey(autoGenerate = true)
        val environmentId: Long = 0L,
        val environmentName: String = "",
        val imageUri: String? = null,
        val projectId: Long
)