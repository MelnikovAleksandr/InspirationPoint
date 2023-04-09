package ru.asmelnikov.inspirationpointtest.data

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel
import ru.asmelnikov.inspirationpointtest.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceDao: DeviceDao
) : DeviceRepository {
    override fun getAllDevices(): Flow<List<DeviceModel>> =
        deviceDao.getAllDevices()


    override suspend fun insertDevices(devicesList: List<DeviceModel>) =
        deviceDao.insertDevices(devicesList)
}