package ru.asmelnikov.inspirationpointtest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel

@Dao
interface ReceivedMessageDao {

    @Query("SELECT * FROM received")
    fun getAllReceivedMessages(): Flow<List<ReceivedMessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceivedMessage(receivedMessageModel: ReceivedMessageModel)
}