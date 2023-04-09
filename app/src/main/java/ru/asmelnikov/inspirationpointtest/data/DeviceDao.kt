package ru.asmelnikov.inspirationpointtest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel

@Dao
interface DeviceDao {

    @Query("SELECT * FROM device")
    fun getAllDevices(): Flow<List<DeviceModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevices(devicesList: List<DeviceModel>)
}