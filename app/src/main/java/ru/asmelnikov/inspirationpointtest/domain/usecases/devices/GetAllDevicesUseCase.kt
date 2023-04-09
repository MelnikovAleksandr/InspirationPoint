package ru.asmelnikov.inspirationpointtest.domain.usecases.devices

import ru.asmelnikov.inspirationpointtest.domain.repository.DeviceRepository
import javax.inject.Inject

class GetAllDevicesUseCase @Inject constructor(
    private val repository: DeviceRepository
) {

    operator fun invoke() = repository.getAllDevices()
}