package ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages

import ru.asmelnikov.inspirationpointtest.domain.repository.SentMessageRepository
import javax.inject.Inject

class GetAllSentMessagesUseCase @Inject constructor(
    private val repository: SentMessageRepository
) {
    operator fun invoke() = repository.getAllSentMessages()
}