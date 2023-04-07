package ru.asmelnikov.inspirationpointtest.domain.usecases

import ru.asmelnikov.inspirationpointtest.domain.repository.SentMessageRepository
import javax.inject.Inject

class GetAllSentMessagesUseCase @Inject constructor(
    private val repository: SentMessageRepository
) {
    operator fun invoke() = repository.getAllSentMessages()
}