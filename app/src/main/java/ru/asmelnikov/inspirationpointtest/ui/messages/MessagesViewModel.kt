package ru.asmelnikov.inspirationpointtest.ui.messages

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

    val allSentMessages: LiveData<List<SentMessageModel>> =
        sentMessageUseCases.getAllSentMessagesUseCase().asLiveData()

    val allReceivedMessages: LiveData<List<ReceivedMessageModel>> =
        receivedMessagesUseCases.getAllReceivedMessagesUseCase().asLiveData()

    fun insertSentMessage(sentMessageModel: SentMessageModel) {
        viewModelScope.launch {
            sentMessageUseCases.insertSentMessageUseCase(sentMessageModel)
        }
    }

    fun insertReceivedMessage(receivedMessageModel: ReceivedMessageModel) {
        viewModelScope.launch {
            receivedMessagesUseCases.insertReceivedMessageUseCase(receivedMessageModel)
        }
    }
}