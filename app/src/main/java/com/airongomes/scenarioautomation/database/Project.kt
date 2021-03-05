package com.airongomes.scenarioautomation.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.airongomes.scenarioautomation.utils.ProjectType

@Entity(tableName = "project_list_table")
data class Project(
        @PrimaryKey(autoGenerate = true)
        val projectId: Long = 0L,
        val projectName: String = "",
        val userName: String = "",
        val address: String = "",
        val type: ProjectType,
        var date: Long
)

class Converter {
        @TypeConverter
        fun fromProjectType(projectType: ProjectType): String {
                return projectType.name
        }

        @TypeConverter
        fun toProjectType(projectType: String): ProjectType {
                return ProjectType.valueOf(projectType)
        }

}