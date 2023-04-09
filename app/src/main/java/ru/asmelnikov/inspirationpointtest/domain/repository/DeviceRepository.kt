package ru.asmelnikov.inspirationpointtest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel

interface DeviceRepository {

    fun getAllDevices(): Flow<List<DeviceModel>>

    suspend fun insertDevices(devicesList: List<DeviceModel>)
}