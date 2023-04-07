package ru.asmelnikov.inspirationpointtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sent")
data class SentMessageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timeStamp: Long,
    val author: String,
    val recipient: String,
    val text: String
)