package com.airongomes.scenarioautomation.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "device_list_table", foreignKeys = [ForeignKey(entity = Environment::class,
    parentColumns = arrayOf("environmentId"),
    childColumns = arrayOf("environmentId"),
    onDelete = ForeignKey.CASCADE)])

data class Device(
    @PrimaryKey(autoGenerate = true)
    val deviceId: Long = 0L,
    val manufacturerName: String,
    val deviceName: String,
    val environmentId: Long
)