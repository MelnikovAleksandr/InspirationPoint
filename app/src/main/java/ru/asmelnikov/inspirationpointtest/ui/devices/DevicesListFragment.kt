package ru.asmelnikov.inspirationpointtest.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.asmelnikov.inspirationpointtest.databinding.FragmentDevicesListBinding
import ru.asmelnikov.inspirationpointtest.domain.model.DeviceModel
import java.util.*

@AndroidEntryPoint
class DevicesListFragment : Fragment() {

    private var _binding: FragmentDevicesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var devicesAdapter: DevicesAdapter

    private val viewModel: DevicesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevicesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        savedInstanceState?.let {
            binding.camNameTextView.text = it.getString("name", "")
            binding.typeTitle.text = it.getString("type", "")
            binding.statusTitle.text = it.getString("status", "")
            binding.macTitle.text = it.getString("mac", "")
            binding.subscriprionTitle.text = it.getString("subscriptions", "")
        }

        viewModel.allDevices.observe(this.viewLifecycleOwner) { devices ->
            devices.let {
                if (devices.isEmpty()) {
                    val fakeDev = viewModel.insertFakeDevices()
                    viewModel.insertDevices(fakeDev)
                } else devicesAdapter.differ.submitList(devices)
            }

            // filter chips
            var currentStatus: String

            val chipGroup = binding.chipGroup
            val status = devices.distinctBy { it.status }.map { it.status }
            for (statuses in status) {
                val chip = Chip(this.requireContext())
                chip.text =
                    statuses.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                chip.isCheckable = true
                chipGroup.addView(chip)

                chip.setOnClickListener {
                    currentStatus = statuses
                    if (chip.isChecked) {
                        updateDeviceList(
                            devices.filter {
                                it.status == currentStatus
                            }
                        )
                    } else devicesAdapter.differ.submitList(devices)
                }
                binding.chipAll.isChecked = true
                binding.chipAll.setOnClickListener {
                    devicesAdapter.differ.submitList(devices)
                }
            }
        }
    }

    private fun initAdapter() {
        devicesAdapter = DevicesAdapter(object : DeviceActionListener {
            override fun onDeviceInfo(device: DeviceModel) {
                binding.apply {
                    camNameTextView.text = device.name
                    typeTitle.text = device.type
                    statusTitle.text = device.status
                    macTitle.text = device.mac
                    subscriprionTitle.text = device.subscriptions
                }
            }
        })
        binding.devicesRecyclerview.apply {
            adapter = devicesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun updateDeviceList(filteredList: List<DeviceModel>) {
        devicesAdapter.differ.submitList(filteredList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.camNameTextView.text.toString())
        outState.putString("type", binding.typeTitle.text.toString())
        outState.putString("status", binding.statusTitle.text.toString())
        outState.putString("mac", binding.macTitle.text.toString())
        outState.putString("subscriptions", binding.subscriprionTitle.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}