package ru.asmelnikov.inspirationpointtest.ui.devices

import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel

interface DeviceActionListener {

    fun onDeviceInfo(device: DeviceModel)
}