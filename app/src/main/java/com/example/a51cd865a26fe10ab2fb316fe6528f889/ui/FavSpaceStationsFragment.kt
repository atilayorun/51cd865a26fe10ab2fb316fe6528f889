package com.example.a51cd865a26fe10ab2fb316fe6528f889.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter.FavSpaceStationsAdapter
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentFavSpaceStationsBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.FavSpaceStationsViewModel


class FavSpaceStationsFragment : Fragment(),
    FavSpaceStationsAdapter.FavSpaceStationsAdapterListener {
    private var _binding: FragmentFavSpaceStationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavSpaceStationsViewModel
    private lateinit var adapter: FavSpaceStationsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavSpaceStationsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupAdapter()
        viewModel = ViewModelProvider(this).get(FavSpaceStationsViewModel()::class.java)
        viewModel.setDb(context?.let { SpaceStationDatabase.getStationDatabase(it) }!!)

        viewModel.favSpaceStationList.observe(viewLifecycleOwner, {
                adapter.setData(it)
        })

        viewModel.getAllFavStation()

        return view
    }

    private fun setupAdapter() {
        adapter = FavSpaceStationsAdapter(this)
        binding.rvFavSpaceStations.adapter = adapter
        binding.rvFavSpaceStations.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun updateStation(station: Station, ivFav: ImageView) {
        if (station.isFav) {
            station.isFav = false
            ivFav.setImageResource(R.drawable.ic_star_black)
            viewModel.updateStation(station)
        } else {
            station.isFav = true
            ivFav.setImageResource(R.drawable.ic_star_border_black)
            viewModel.updateStation(station)
        }

    }
}
