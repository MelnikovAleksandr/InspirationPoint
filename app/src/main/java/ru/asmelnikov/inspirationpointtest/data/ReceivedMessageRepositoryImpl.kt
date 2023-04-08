package ru.asmelnikov.inspirationpointtest.data

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel
import ru.asmelnikov.inspirationpointtest.domain.repository.ReceivedMessagesRepository
import javax.inject.Inject

class ReceivedMessageRepositoryImpl @Inject constructor(
    private val receivedMessageDao: ReceivedMessageDao
) : ReceivedMessagesRepository {

    override fun getAllReceivedMessages(): Flow<List<ReceivedMessageModel>> =
        receivedMessageDao.getAllReceivedMessages()

    override suspend fun insertReceivedMessage(receivedMessageModel: ReceivedMessageModel) {
        receivedMessageDao.insertReceivedMessage(receivedMessageModel)
    }

}