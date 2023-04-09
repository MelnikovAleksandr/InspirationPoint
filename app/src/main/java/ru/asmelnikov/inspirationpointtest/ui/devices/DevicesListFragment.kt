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
            binding.typeTitleTextView.text = it.getString("type", "")
            binding.statusTitleTextView.text = it.getString("status", "")
            binding.macTitleTextView.text = it.getString("mac", "")
            binding.subscriprionTitleTextView.text = it.getString("subscriptions", "")
        }

        viewModel.allDevices.observe(this.viewLifecycleOwner) { devices ->
            devices.let {
                if (devices.isEmpty()) {
                    val fakeDev = viewModel.insertFakeDevices()
                    viewModel.insertDevices(fakeDev)
                } else devicesAdapter.differ.submitList(devices)
            }
        }
    }

    private fun initAdapter() {
        devicesAdapter = DevicesAdapter(object : DeviceActionListener {
            override fun onDeviceInfo(device: DeviceModel) {
                binding.apply {
                    camNameTextView.text = device.name
                    typeTitleTextView.text = "Type: ${device.type}"
                    statusTitleTextView.text = "Status: ${device.status}"
                    macTitleTextView.text = "MAC: ${device.mac}"
                    subscriprionTitleTextView.text = "Subscription: ${device.subscriptions}"
                }
            }
        })
        binding.devicesRecyclerview.apply {
            adapter = devicesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.camNameTextView.text.toString())
        outState.putString("type", binding.typeTitleTextView.text.toString())
        outState.putString("status", binding.statusTitleTextView.text.toString())
        outState.putString("mac", binding.macTitleTextView.text.toString())
        outState.putString("subscriptions", binding.subscriprionTitleTextView.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}