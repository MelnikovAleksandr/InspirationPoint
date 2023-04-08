package ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages

data class ReceivedMessagesUseCases(
    val insertReceivedMessageUseCase: InsertReceivedMessageUseCase,
    val getAllReceivedMessagesUseCase: GetAllReceivedMessagesUseCase
)