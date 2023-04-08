package ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages

import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel
import ru.asmelnikov.inspirationpointtest.domain.repository.SentMessageRepository
import javax.inject.Inject

class InsertSentMessageUseCase @Inject constructor(
    private val repository: SentMessageRepository
) {
    suspend operator fun invoke(sentMassageModel: SentMessageModel) =
        repository.insertSentMessage(sentMassageModel)
}