package ru.asmelnikov.inspirationpointtest.ui.devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel
import ru.asmelnikov.inspirationpointtest.domain.usecases.devices.DevicesUseCases
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val devicesUseCases: DevicesUseCases
) : ViewModel() {

    init {
        val devices = insertFakeDevices()
        insertDevices(devices)
    }

    val allDevices: LiveData<List<DeviceModel>> =
        devicesUseCases.getAllDevicesUseCase().asLiveData()

    fun insertDevices(devices: List<DeviceModel>) {
        viewModelScope.launch {
            devicesUseCases.insertDevicesUseCase(devices)
        }
    }

    fun insertFakeDevices(): List<DeviceModel> {
        return listOf(
            DeviceModel(
                id = 1,
                name = "Cam-5",
                type = "Camera",
                status = "live",
                mac = "fe:fe:F3:fe",
                subscriptions = "SM-03"
            ),
            DeviceModel(
                id = 2,
                name = "Rep-1",
                type = "Camera",
                status = "dead",
                mac = "fe:fe:F3:fe",
                subscriptions = "SM-03"
            ),
            DeviceModel(
                id = 3,
                name = "LD-4",
                type = "LiveData",
                status = "mute",
                mac = "fe:fe:F3:fe",
                subscriptions = "SM-03"
            ),
            DeviceModel(
                id = 4,
                name = "Dmitrii",
                type = "Participant",
                status = "approved",
                mac = "fe:fe:F3:fe",
                subscriptions = "no"
            ),
            DeviceModel(
                id = 5,
                name = "Cam-5",
                type = "Camera",
                status = "live",
                mac = "fe:fe:F3:fe",
                subscriptions = "SM-03"
            ),
            DeviceModel(
                id = 6,
                name = "Rep-1",
                type = "Camera",
                status = "dead",
                mac = "fe:fe:F3:fe",
                subscriptions = "SM-03"
            ),
            DeviceModel(
                id = 7,
                name = "LD-4",
                type = "LiveData",
                status = "mute",
                mac = "fe:fe:F3:fe",
                subscriptions = "SM-03"
            ),
            DeviceModel(
                id = 8,
                name = "Dmitrii",
                type = "Participant",
                status = "approved",
                mac = "fe:fe:F3:fe",
                subscriptions = "no"
            )
        )
    }
}