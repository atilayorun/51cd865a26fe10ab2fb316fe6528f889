package com.example.a51cd865a26fe10ab2fb316fe6528f889.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter.SpaceStationAdapter
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.FragmentStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Spacecraft
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util
import com.example.a51cd865a26fe10ab2fb316fe6528f889.viewModel.SpaceStationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpaceStationFragment : Fragment(), SpaceStationAdapter.StationAdapterListener {
    private var _binding: FragmentStationBinding? = null
    private val binding get() = _binding!!
    private val viewModelSpace: SpaceStationViewModel by viewModels()
    private lateinit var adapterSpace: SpaceStationAdapter
    private lateinit var spaceCraft: Spacecraft
    private var gameOver: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStationBinding.inflate(inflater, container, false)
        val view = binding.root

        setupAdapter()
        viewModelSetObserver()
        listeners()

        viewModelSpace.getAllData()
        return view
    }

    private fun listeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapterSpace.filter(p0.toString())
                return false
            }
        })
    }

    private fun viewModelSetObserver() {
        viewModelSpace.canGo.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Yetersiz kaynaktan dolayı gidilemiyor.", Toast.LENGTH_SHORT)
                .show()
        })

        viewModelSpace.spacecraftLiveData.observe(viewLifecycleOwner, {
            spaceCraft = it
            if (spaceCraft.damageCapacity == 0 || spaceCraft.enduranceTime == 0 || spaceCraft.spaceSuitCount == 0 || spaceCraft.universalSpaceTime == 0) {
                gameOver = true
                it.currentPositionName = "Dünya"
                Toast.makeText(
                    context,
                    "Oyun bitti. Tekrardan başlamak için ilk sayfaya dönün.",
                    Toast.LENGTH_SHORT
                )
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

        viewModelSpace.spaceStationListLiveData.observe(viewLifecycleOwner, {
            it.map { x ->
                x.distanceToSpacecraft = Util.distanceFormula(
                    x.coordinateX,
                    spaceCraft.coordinateX,
                    x.coordinateY,
                    spaceCraft.coordinateY
                )
            }
            adapterSpace.setData(it as ArrayList<SpaceStation>)
        })
    }


    override fun scrollToNext(position: Int) {
        binding.rvStation.smoothScrollToPosition(position)
    }

    override fun scrollToPrevious(position: Int) {
        binding.rvStation.smoothScrollToPosition(position)
    }

    private fun setupAdapter() {
        adapterSpace = SpaceStationAdapter(this)
        binding.rvStation.adapter = adapterSpace
        binding.rvStation.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun imgStarOnClickListener(spaceStation: SpaceStation, ivStar: ImageView) {
        if (!spaceStation.isFav) {
            spaceStation.isFav = true
            viewModelSpace.updateStation(spaceStation)
            ivStar.setImageResource(R.drawable.ic_star_black)
        }
    }

    override fun btnTravelSetOnClickListener(spaceStation: SpaceStation) {
        if (gameOver)
            Toast.makeText(
                context,
                "Oyun bitti. Tekrardan başlamak için ilk sayfaya dönün.",
                Toast.LENGTH_SHORT
            )
                .show()
        else {
            when {
                binding.tvCurrentStation.text == spaceStation.name -> Toast.makeText(
                    context,
                    "Zaten bu istasyondasınız.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                spaceStation.capacity == spaceStation.stock -> Toast.makeText(
                    context,
                    "Gittiğiniz istasyona tekrardan gidemezsiniz.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                binding.tvCurrentStation.text != spaceStation.name -> {
                    viewModelSpace.btnTravelSetOnClick(spaceStation)
                }
            }
        }
    }
}
