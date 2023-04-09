package ru.asmelnikov.inspirationpointtest.ui.devices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.asmelnikov.inspirationpointtest.databinding.DevicesListItemBinding
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel

class DevicesAdapter(private val deviceActionListener: DeviceActionListener) :
    RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder>() {

    class DevicesViewHolder(deviceBinding: DevicesListItemBinding) :
        RecyclerView.ViewHolder(deviceBinding.root) {

        private val binding = deviceBinding

        fun bind(deviceModel: DeviceModel) {
            binding.apply {
                nameTextview.text = deviceModel.name
                typeTextview.text = deviceModel.type
                statusTextView.text = deviceModel.status
                macTextview.text = deviceModel.mac
                subscriptionsTextview.text = deviceModel.subscriptions
            }
        }
    }

    private val callBack = object : DiffUtil.ItemCallback<DeviceModel>() {

        override fun areItemsTheSame(oldItem: DeviceModel, newItem: DeviceModel): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DeviceModel, newItem: DeviceModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        val binding =
            DevicesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DevicesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val device = differ.currentList[position]
        holder.bind(device)
        holder.itemView.apply {
            setOnClickListener {
                deviceActionListener.onDeviceInfo(device)
            }
        }
    }
}