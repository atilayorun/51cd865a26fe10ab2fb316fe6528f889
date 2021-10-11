package com.example.a51cd865a26fe10ab2fb316fe6528f889.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter.StationAdapter
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.db.SpaceStationDatabase
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.StationViewModel
import java.text.FieldPosition

class StationFragment : Fragment(), StationAdapter.StationAdapterListener {
    private var _binding: FragmentStationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StationViewModel
    private lateinit var adapter: StationAdapter
    private lateinit var spaceCraft: Spacecraft
    private var gameOver: Boolean = false

    override fun onStart() {
        super.onStart()
        setupAdapter()
        viewModelSetObserver()
        listeners()

        viewModel.getAllData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStationBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(StationViewModel()::class.java)
        viewModel.setDb(context?.let { SpaceStationDatabase.getStationDatabase(it) }!!)


        return view
    }

    private fun listeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter(p0.toString())
                return false
            }
        })
    }

    private fun viewModelSetObserver() {
        viewModel.canGo.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Yetersiz kaynaktan dolayı gidilemiyor.", Toast.LENGTH_SHORT)
                .show()
        })

        viewModel.spacecraftLiveData.observe(viewLifecycleOwner, {
            spaceCraft = it
            if (spaceCraft.damageCapacity == 0 || spaceCraft.enduranceTime == 0 || spaceCraft.spaceSuitCount == 0 || spaceCraft.universalSpaceTime == 0) {
                gameOver = true
                it.currentPositionName = "Dünya"
                Toast.makeText(context, "Oyun bitti.", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.tvSpaceSuitCount.text = "UGS : ${it.spaceSuitCount}"
            binding.tvUniversalSpaceTime.text = "EUS : ${it.universalSpaceTime}"
            binding.tvEnduranceTime.text = "DS : ${it.enduranceTime}"
            binding.tvSpaceCraftName.text = it.name
            binding.tvDamageCapacity.text = it.damageCapacity.toString()
            binding.tvCurrentStation.text = it.currentPositionName
            binding.tvTime.text = "${it.enduranceTime / 1000}s"
        })

        viewModel.spaceStationListLiveData.observe(viewLifecycleOwner, {
            it.map { x ->
                x.distanceToSpacecraft = Util.distanceFormula(
                    x.coordinateX,
                    spaceCraft.coordinateX,
                    x.coordinateY,
                    spaceCraft.coordinateY
                )
            }
            adapter.setData(it as ArrayList<Station>)
        })
    }


    override fun scrollToNext(position: Int) {
        binding.rvStation.smoothScrollToPosition(position)
    }

    override fun scrollToPrevious(position: Int) {
        binding.rvStation.smoothScrollToPosition(position)
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
            ivStar.setImageResource(R.drawable.ic_star_black)
        }
    }

    override fun btnTravelSetOnClickListener(station: Station) {
        if (gameOver)
            Toast.makeText(context, "Oyun bitti.", Toast.LENGTH_SHORT)
                .show()
        else {
            if (binding.tvCurrentStation.text != station.name) {
                viewModel.btnTravelSetOnClick(station)
            } else
                Toast.makeText(context, "Zaten bu istasyondasınız.", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}
