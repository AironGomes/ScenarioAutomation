package com.airongomes.scenarioautomation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Project::class, Environment::class, Device::class], version = 5, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract val projectDao: ProjectDao
    abstract val environmentDao: EnvironmentDao
    abstract val deviceDao: DeviceDao

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