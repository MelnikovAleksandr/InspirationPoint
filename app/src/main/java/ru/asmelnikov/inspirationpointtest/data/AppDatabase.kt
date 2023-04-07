package ru.asmelnikov.inspirationpointtest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel

@Database(entities = [SentMessageModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sentMessageDao(): SentMessageDao
}