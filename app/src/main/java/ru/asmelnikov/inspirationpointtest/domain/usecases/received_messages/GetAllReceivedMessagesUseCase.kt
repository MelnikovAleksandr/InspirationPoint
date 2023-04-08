package ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages

import ru.asmelnikov.inspirationpointtest.domain.repository.ReceivedMessagesRepository
import javax.inject.Inject

class GetAllReceivedMessagesUseCase @Inject constructor(
    private val repository: ReceivedMessagesRepository
) {

    operator fun invoke() = repository.getAllReceivedMessages()
}