package com.example.a51cd865a26fe10ab2fb316fe6528f889

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter.StationAdapter
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.StationViewModel

class StationFragment : Fragment(), StationAdapter.StationAdapterListener {
    private var _binding: FragmentStationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StationViewModel
    private lateinit var adapter: StationAdapter
    private lateinit var spacecraft: Spacecraft
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStationBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(StationViewModel()::class.java)

        viewModel.setDb(context?.let { SpaceStationDatabase.getStationDatabase(it) }!!)
        spacecraft = viewModel.getSpacecraft()
        binding.tvSpaceCraftName.text = spacecraft.name

        setupAdapter()

        binding.rvStation.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            return@OnTouchListener true
        })

        viewModel.spaceStationList.observe(viewLifecycleOwner, {
            if (it.isEmpty())
                viewModel.getAllStationFromAPI()

            // Düzelt = her geldiğinde ekleme yapıyor
            viewModel.addStation()
            adapter.setData(it as ArrayList<Station>)
        })
        viewModel.getAllStation()

        binding.ivRightArrow.setOnClickListener {
            scrollToNext()
        }

        binding.ivLeftArrow.setOnClickListener {
            scrollToPrevious()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter(p0.toString())
                return false
            }
        })

        return view
    }

    private fun scrollToNext() {
        if (currentPosition != viewModel.spaceStationList.value?.size?.minus(1)) {
            ++currentPosition
            binding.rvStation.layoutManager?.smoothScrollToPosition(
                binding.rvStation,
                RecyclerView.State(),
                currentPosition
            )
        }
    }

    private fun scrollToPrevious() {
        if (currentPosition != 0) {
            --currentPosition
            binding.rvStation.layoutManager?.smoothScrollToPosition(
                binding.rvStation,
                RecyclerView.State(),
                currentPosition
            )
        }
    }

    private fun setupAdapter() {
        adapter = StationAdapter(this)
        binding.rvStation.adapter = adapter
        binding.rvStation.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun imgStarOnClickListener(station: Station, ivStar: ImageView) {
        if (!station.isFav) {
            station.isFav = true
            viewModel.updateStation(station)
            ivStar.setImageResource(R.drawable.blackstar)
        }
    }
}
