package ru.asmelnikov.inspirationpointtest.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.asmelnikov.inspirationpointtest.databinding.MessagesItemBinding
import ru.asmelnikov.inspirationpointtest.domain.model.Message
import ru.asmelnikov.inspirationpointtest.domain.model.ReceivedMessageModel
import ru.asmelnikov.inspirationpointtest.domain.model.SentMessageModel

class MessagesAdapter :
    RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder<Message>>() {

    class MessagesViewHolder<T : Message>(messageBinding: MessagesItemBinding) :
        RecyclerView.ViewHolder(messageBinding.root) {

        private val binding = messageBinding

        fun bind(message: T) {
            if (message is SentMessageModel) {
                binding.idTextView.text = message.id.toString()
                binding.timeTextView.text = message.timeStamp
                binding.recepientTextView.text = message.recipient
                binding.dateTextView.text = message.date
                binding.contentTextView.text = message.text
            } else if (message is ReceivedMessageModel) {
                binding.idTextView.text = message.id.toString()
                binding.timeTextView.text = message.timeStamp
                binding.recepientTextView.text = message.author
                binding.dateTextView.text = message.date
                binding.contentTextView.text = message.text
            }
        }
    }


    private val callBack = object : DiffUtil.ItemCallback<Message>() {

        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            var result = false
            if (oldItem is SentMessageModel && newItem is SentMessageModel) {
                result = oldItem.id == newItem.id
            } else if (oldItem is ReceivedMessageModel && newItem is ReceivedMessageModel) {
                result = oldItem.id == newItem.id
            }
            return result
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            var result = false
            if (oldItem is SentMessageModel && newItem is SentMessageModel) {
                result = oldItem == newItem
            } else if (oldItem is ReceivedMessageModel && newItem is ReceivedMessageModel) {
                result = oldItem == newItem
            }
            return result
        }
    }


    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder<Message> {
        val binding =
            MessagesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessagesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MessagesViewHolder<Message>, position: Int) {
        val sentMessage = differ.currentList[position]
        holder.bind(sentMessage)
    }
}
