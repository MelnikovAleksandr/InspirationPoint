package ru.asmelnikov.inspirationpointtest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.asmelnikov.inspirationpointtest.databinding.MessagesItemBinding
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel

class MessagesAdapter :
    RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    class MessagesViewHolder(messageBinding: MessagesItemBinding) :
        RecyclerView.ViewHolder(messageBinding.root) {

        private val binding = messageBinding

        fun bind(message: SentMessageModel) {
            binding.idTextView.text = message.id.toString()
            binding.timeTextView.text = System.currentTimeMillis().toString()
            binding.recepientTextView.text = message.recipient
            binding.contentTextView.text = message.text
        }
    }


    private val callBack = object : DiffUtil.ItemCallback<SentMessageModel>() {
        override fun areItemsTheSame(
            oldItem: SentMessageModel,
            newItem: SentMessageModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SentMessageModel,
            newItem: SentMessageModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val binding =
            MessagesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val sentMessage = differ.currentList[position]
        holder.bind(sentMessage)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
