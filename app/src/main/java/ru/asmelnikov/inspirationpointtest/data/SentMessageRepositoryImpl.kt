package ru.asmelnikov.inspirationpointtest.data

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel
import ru.asmelnikov.inspirationpointtest.domain.repository.SentMessageRepository
import javax.inject.Inject

class SentMessageRepositoryImpl @Inject constructor(
    private val sentMessageDao: SentMessageDao
) : SentMessageRepository {

    override fun getAllSentMessages(): Flow<List<SentMessageModel>> =
        sentMessageDao.getAllSentMessages()

    override suspend fun insertSentMessage(sentMessageModel: SentMessageModel) =
        sentMessageDao.insertSentMessage(sentMessageModel)

}