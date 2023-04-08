package ru.asmelnikov.inspirationpointtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "received")
data class ReceivedMessageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timeStamp: String,
    val date: String,
    val author: String,
    val recipient: String,
    val text: String
) : Message