package ru.asmelnikov.inspirationpointtest.domain.usecases

data class SentMessageUseCases(
    val insertSentMessageUseCase: InsertSentMessageUseCase,
    val getAllSentMessagesUseCase: GetAllSentMessagesUseCase
)
