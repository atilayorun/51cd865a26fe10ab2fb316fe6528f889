package com.example.a51cd865a26fe10ab2fb316fe6528f889.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter.FavSpaceStationsAdapter
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentFavSpaceStationsBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.FavSpaceStationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavSpaceStationsFragment : Fragment(),
    FavSpaceStationsAdapter.FavSpaceStationsAdapterListener {
    private var _binding: FragmentFavSpaceStationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavSpaceStationsViewModel by viewModels()
    private lateinit var adapter: FavSpaceStationsAdapter


    override fun onStart() {
        super.onStart()
        setupAdapter()
        viewModelSetObserver()
        viewModel.getAllFavStation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavSpaceStationsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun viewModelSetObserver() {
        viewModel.favSpaceStationListLiveData.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
    }

    private fun setupAdapter() {
        adapter = FavSpaceStationsAdapter(this)
        binding.rvFavSpaceStations.adapter = adapter
        binding.rvFavSpaceStations.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun updateStation(spaceStation: SpaceStation, ivFav: ImageView) {
        spaceStation.isFav = false
        viewModel.updateAndGetAllFavStation(spaceStation)
    }
}
