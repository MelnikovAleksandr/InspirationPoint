package ru.asmelnikov.inspirationpointtest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel

@Dao
interface SentMessageDao {

    @Query("SELECT * FROM sent")
    fun getAllSentMessages(): Flow<List<SentMessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentMessage(sentMassageModel: SentMessageModel)

}