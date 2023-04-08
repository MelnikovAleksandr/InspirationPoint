package ru.asmelnikov.inspirationpointtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel
import ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages.ReceivedMessagesUseCases
import ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages.SentMessageUseCases
import ru.asmelnikov.inspirationpointtest.utils.Constants.USER
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val sentMessageUseCases: SentMessageUseCases,
    private val receivedMessagesUseCases: ReceivedMessagesUseCases
) : ViewModel() {

    init {
        // fake initial data
        insertReceivedMessage(
            ReceivedMessageModel(
                id = 1,
                author = USER,
                recipient = "Gleb",
                text = "Approve all LiveData",
                timeStamp = "13:54:07",
                date = "03.12.2022"

            )
        )
        insertReceivedMessage(
            ReceivedMessageModel(
                id = 2,
                author = USER,
                recipient = "Gleb",
                text = "check CAM-05",
                timeStamp = "13:56:20",
                date = "03.12.2022"
            )
        )
        insertReceivedMessage(
            ReceivedMessageModel(
                id = 3,
                author = USER,
                recipient = "Gleb",
                text = "check CAM-07",
                timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                date = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )
        )
        insertSentMessage(
            SentMessageModel(
                recipient = "Viktor",
                author = USER,
                text = "CAM-05 is down",
                timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                date = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                id = 1
            )
        )
        insertSentMessage(
            SentMessageModel(
                recipient = "Viktor",
                author = USER,
                text = "Go to the piste 6",
                timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                date = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                id = 2
            )
        )
        insertSentMessage(
            SentMessageModel(
                recipient = "Viktor",
                author = USER,
                text = "CAM-02 is down again",
                timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                date = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                id = 3
            )
        )
    }

    val allSentMessages: LiveData<List<SentMessageModel>> =
        sentMessageUseCases.getAllSentMessagesUseCase().asLiveData()

    val allReceivedMessages: LiveData<List<ReceivedMessageModel>> =
        receivedMessagesUseCases.getAllReceivedMessagesUseCase().asLiveData()

    fun insertSentMessage(sentMessageModel: SentMessageModel) {
        viewModelScope.launch {
            sentMessageUseCases.insertSentMessageUseCase(sentMessageModel)
        }
    }

    private fun insertReceivedMessage(receivedMessageModel: ReceivedMessageModel) {
        viewModelScope.launch {
            receivedMessagesUseCases.insertReceivedMessageUseCase(receivedMessageModel)
        }
    }
}