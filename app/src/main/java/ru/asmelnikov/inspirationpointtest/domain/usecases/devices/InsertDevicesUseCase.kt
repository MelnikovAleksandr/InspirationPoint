package ru.asmelnikov.inspirationpointtest.domain.usecases.devices

import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel
import ru.asmelnikov.inspirationpointtest.domain.repository.DeviceRepository
import javax.inject.Inject

class InsertDevicesUseCase @Inject constructor(
    private val repository: DeviceRepository
) {

    suspend operator fun invoke(devicesList: List<DeviceModel>) =
        repository.insertDevices(devicesList)
}