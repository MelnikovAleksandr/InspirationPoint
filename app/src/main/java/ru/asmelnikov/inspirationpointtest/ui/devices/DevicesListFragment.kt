package ru.asmelnikov.inspirationpointtest.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
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
        viewModel.selectedDevice.observe(this.viewLifecycleOwner) { device ->

            binding.apply {
                camNameTextView.text = device?.name ?: ""
                typeTitle.text = device?.type ?: ""
                statusTitle.text = device?.status ?: ""
                macTitle.text = device?.mac ?: ""
                subscriprionTitle.text = device?.subscriptions ?: ""
            }
        }

        viewModel.allDevices.observe(this.viewLifecycleOwner) { devices ->
            devices.let {
                if (devices.isEmpty()) {
                    val fakeDev = viewModel.insertFakeDevices()
                    viewModel.insertDevices(fakeDev)
                } else devicesAdapter.differ.submitList(devices)
            }

            val chipGroup = binding.chipGroup
            val status = devices.distinctBy { it.status }.map { it.status }
            for (statuses in status) {
                val chip = Chip(requireContext())
                chip.text =
                    statuses.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                chip.isCheckable = true
                chipGroup.addView(chip)

                chip.setOnClickListener {
                    if (chip.isChecked) {
                        viewModel.filterDevices(devices, statuses)
                    } else if (!chip.isChecked) {
                        viewModel.filterDevices(devices, null)
                    } else {
                        binding.chipAll.isChecked = true
                        devicesAdapter.differ.submitList(devices)
                    }
                }
            }

            viewModel.devicesLiveData.observe(viewLifecycleOwner) { filteredDevices ->
                devicesAdapter.differ.submitList(filteredDevices)
            }

            viewModel.selectedStatus.observe(viewLifecycleOwner) { selectedStatus ->
                binding.chipGroup.children.filterIsInstance<Chip>().forEach { chip ->
                    chip.isChecked = chip.text.toString().lowercase() == selectedStatus?.lowercase()
                }
            }
            binding.chipAll.setOnClickListener {
                devicesAdapter.differ.submitList(devices)
            }
        }
    }

    private fun initAdapter() {
        devicesAdapter = DevicesAdapter(object : DeviceActionListener {
            override fun onDeviceInfo(device: DeviceModel) {
                viewModel.saveDeviceSelection(device)
            }
        })
        binding.devicesRecyclerview.apply {
            adapter = devicesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}