package ru.asmelnikov.inspirationpointtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel
import ru.asmelnikov.inspirationpointtest.domain.usecases.SentMessageUseCases
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messageUseCases: SentMessageUseCases
) : ViewModel() {

    val allSentMessages: LiveData<List<SentMessageModel>> =
        messageUseCases.getAllSentMessagesUseCase().asLiveData()

    fun insertSentMessage(sentMessageModel: SentMessageModel) {
        viewModelScope.launch {
            messageUseCases.insertSentMessageUseCase(sentMessageModel)
        }
    }
}