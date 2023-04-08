package ru.asmelnikov.inspirationpointtest.ui

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
import ru.asmelnikov.inspirationpointtest.databinding.FragmentMessagesBinding
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
            periodTextView.text = "Today"
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
                sentAdapter.differ.submitList(it)
            }
        }

        viewModel.allReceivedMessages.observe(this.viewLifecycleOwner) { received ->
            received.let {
                receivedAdapter.differ.submitList(it)
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
        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}