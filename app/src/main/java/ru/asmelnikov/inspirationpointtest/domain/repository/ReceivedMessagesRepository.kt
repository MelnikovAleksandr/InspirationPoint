package ru.asmelnikov.inspirationpointtest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel

interface ReceivedMessagesRepository {

    fun getAllReceivedMessages(): Flow<List<ReceivedMessageModel>>

    suspend fun insertReceivedMessage(receivedMessageModel: ReceivedMessageModel)
}