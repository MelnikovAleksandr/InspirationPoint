package ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages

import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel
import ru.asmelnikov.inspirationpointtest.domain.repository.ReceivedMessagesRepository
import javax.inject.Inject

class InsertReceivedMessageUseCase @Inject constructor(
    private val repository: ReceivedMessagesRepository
) {
    suspend operator fun invoke(receivedMessageModel: ReceivedMessageModel) =
        repository.insertReceivedMessage(receivedMessageModel)
}