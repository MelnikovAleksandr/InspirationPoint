package ru.asmelnikov.inspirationpointtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.asmelnikov.inspirationpointtest.databinding.FragmentMessagesBinding
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel

@AndroidEntryPoint
class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var sentAdapter: MessagesAdapter

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
        initAdapter()

        viewModel.allSentMessages.observe(this.viewLifecycleOwner) { sentMessages ->
            if (sentMessages.isEmpty()) {
                viewModel.insertSentMessage(
                    SentMessageModel(
                        recipient = "Viktor",
                        author = "Max",
                        text = "TEST",
                        timeStamp = System.currentTimeMillis(),
                        id = 1
                    )
                )
                viewModel.insertSentMessage(
                    SentMessageModel(
                        recipient = "Viktor",
                        author = "Max",
                        text = "TEST2",
                        timeStamp = System.currentTimeMillis(),
                        id = 2
                    )
                )
                viewModel.insertSentMessage(
                    SentMessageModel(
                        recipient = "Viktor",
                        author = "Max",
                        text = "TEST3",
                        timeStamp = System.currentTimeMillis(),
                        id = 3
                    )
                )
            }

            sentMessages.let {
                sentAdapter.differ.submitList(it)
            }
        }

    }

    private fun initAdapter() {
        sentAdapter = MessagesAdapter()
        binding.sentRecyclerView.apply {
            adapter = sentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}