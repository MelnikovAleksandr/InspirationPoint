package ru.asmelnikov.inspirationpointtest.ui.devices

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel
import ru.asmelnikov.inspirationpointtest.domain.usecases.devices.DevicesUseCases
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val devicesUseCases: DevicesUseCases
) : ViewModel() {

    private val _devicesLiveData = MutableLiveData<List<DeviceModel>>()
    private val _selectedStatus = MutableLiveData<String?>()
    private val _selectedDevice = MutableLiveData<DeviceModel?>()
    val devicesLiveData get() = _devicesLiveData
    val selectedStatus get() = _selectedStatus
    val selectedDevice: LiveData<DeviceModel?> = _selectedDevice

    val allDevices: LiveData<List<DeviceModel>> =
        devicesUseCases.getAllDevicesUseCase().asLiveData()

    fun saveDeviceSelection(device: DeviceModel) {
        _selectedDevice.value = device
    }
    fun filterDevices(devices: List<DeviceModel>, status: String?) {
        if (status != null) {
            _devicesLiveData.value = devices.filter { it.status == status }
            _selectedStatus.value = status
        } else {
            _devicesLiveData.value = devices
            _selectedStatus.value = null
        }
    }
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
                status = "blocked",
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
                status = "blocked",
                mac = "fe:fe:F3:fe",
                subscriptions = "no"
            )
        )
    }
}