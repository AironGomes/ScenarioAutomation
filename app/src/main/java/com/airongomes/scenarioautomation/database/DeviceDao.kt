package com.airongomes.scenarioautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/** DAO para Device */
@Dao
interface DeviceDao {

    @Insert
    suspend fun insertDevice(device: Device)

    @Update
    suspend fun updateEnvironment(device: Device)

    @Query("SELECT * from device_list_table WHERE deviceId = :deviceId")
    fun getDevice(deviceId: Long): LiveData<Device>

    @Query("SELECT * from device_list_table WHERE environmentId = :environmentId")
    fun getDeviceList(environmentId: Long): LiveData<List<Device>>

    @Query("DELETE from device_list_table WHERE deviceId = :deviceId")
    suspend fun deleteDevice(deviceId: Long)
}