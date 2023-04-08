package ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages

data class SentMessageUseCases(
    val insertSentMessageUseCase: InsertSentMessageUseCase,
    val getAllSentMessagesUseCase: GetAllSentMessagesUseCase
)
