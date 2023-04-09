package ru.asmelnikov.inspirationpointtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device")
data class DeviceModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val status: String,
    val mac: String,
    val subscriptions: String
)
