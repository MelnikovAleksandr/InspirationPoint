package ru.asmelnikov.inspirationpointtest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel

interface SentMessageRepository {

    fun getAllSentMessages(): Flow<List<SentMessageModel>>

    suspend fun insertSentMessage(sentMessageModel: SentMessageModel)

}