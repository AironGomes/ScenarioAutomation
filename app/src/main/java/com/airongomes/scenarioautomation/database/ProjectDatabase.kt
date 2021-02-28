package com.airongomes.scenarioautomation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Project::class], version = 1, exportSchema = false)
abstract class ProjectDatabase: RoomDatabase() {

    abstract val projectDao: ProjectDao

    companion object {

        private lateinit var INSTANCE: ProjectDatabase

        fun getInstance(context: Context): ProjectDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectDatabase::class.java,
                        "projectDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}