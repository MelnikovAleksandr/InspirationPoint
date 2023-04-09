package ru.asmelnikov.inspirationpointtest.ui.messages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.asmelnikov.inspirationpointtest.R
import ru.asmelnikov.inspirationpointtest.databinding.FragmentMessagesBinding
import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel
import ru.asmelnikov.inspirationpointtest.utils.Constants.USER
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var sentAdapter: MessagesAdapter
    private lateinit var receivedAdapter: MessagesAdapter

    private val viewModel: MessagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSentAdapter()
        initReceivedAdapter()

        binding.apply {
            nameTextView.text = USER
            periodTextView.text = getString(R.string.today)
            sentButton.setOnClickListener {
                val recepient = recepientEditText.text.toString()
                val content = textEditText.text.toString()

                if (recepient.isEmpty() || content.isEmpty()) {
                    showErrorToast(requireContext())
                } else {
                    val sentMessage = createMessage(recepient, content)
                    insertMessage(sentMessage)
                    sentRecyclerView.smoothScrollToPosition(sentRecyclerView.adapter!!.itemCount + 1)
                    recepientEditText.text = null
                    textEditText.text = null
                }
            }
        }

        viewModel.allSentMessages.observe(this.viewLifecycleOwner) { sentMessages ->
            sentMessages.let {
                if (sentMessages.isEmpty()) { // fake init data
                    viewModel.insertSentMessage(
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
                    viewModel.insertSentMessage(
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
                    viewModel.insertSentMessage(
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
                } else sentAdapter.differ.submitList(it)
            }
        }

        viewModel.allReceivedMessages.observe(this.viewLifecycleOwner) { received ->
            received.let {
                if (received.isEmpty()) { // fake init data
                    viewModel.insertReceivedMessage(
                        ReceivedMessageModel(
                            id = 1,
                            author = USER,
                            recipient = "Gleb",
                            text = "Approve all LiveData",
                            timeStamp = "13:54:07",
                            date = "03.12.2022"

                        )
                    )
                    viewModel.insertReceivedMessage(
                        ReceivedMessageModel(
                            id = 2,
                            author = USER,
                            recipient = "Gleb",
                            text = "check CAM-05",
                            timeStamp = "13:56:20",
                            date = "03.12.2022"
                        )
                    )
                    viewModel.insertReceivedMessage(
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
                } else receivedAdapter.differ.submitList(it)
            }
        }
    }

    private fun insertMessage(sentMessage: SentMessageModel) {
        viewModel.insertSentMessage(sentMessage)
    }

    private fun createMessage(recepient: String, content: String): SentMessageModel {
        return SentMessageModel(
            recipient = recepient,
            author = USER,
            text = content,
            timeStamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            id = 0
        )
    }

    private fun initSentAdapter() {
        sentAdapter = MessagesAdapter()
        binding.sentRecyclerView.apply {
            adapter = sentAdapter
            layoutManager = LinearLayoutManager(activity)
            (layoutManager as LinearLayoutManager).reverseLayout = true
            (layoutManager as LinearLayoutManager).stackFromEnd = true
        }
    }

    private fun initReceivedAdapter() {
        receivedAdapter = MessagesAdapter()
        binding.receivedRecyclerView.apply {
            adapter = receivedAdapter
            layoutManager = LinearLayoutManager(activity)
            (layoutManager as LinearLayoutManager).reverseLayout = true
            (layoutManager as LinearLayoutManager).stackFromEnd = true
        }
    }

    private fun showErrorToast(context: Context) {
        Toast.makeText(context, "Fill in all fields, please", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}