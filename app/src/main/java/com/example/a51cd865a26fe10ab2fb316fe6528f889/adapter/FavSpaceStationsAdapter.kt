package com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.ItemFavStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.ItemStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import kotlinx.android.synthetic.main.item_fav_station.view.*
import kotlinx.android.synthetic.main.item_station.view.tv_stationName

class FavSpaceStationsAdapter(listener: FavSpaceStationsAdapterListener): RecyclerView.Adapter<FavSpaceStationsAdapter.ViewHolder>(){
    var listener: FavSpaceStationsAdapterListener = listener
    private var stationList: List<Station> = listOf()

    inner class ViewHolder(val binding: ItemFavStationBinding) : RecyclerView.ViewHolder(binding.root){
        val tvStationName: TextView = binding.tvStationName
        val tvUniversalSpaceTime: TextView = binding.tvUniversalSpaceTime
        val ivFav:ImageView = binding.ivFav2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: ItemFavStationBinding = ItemFavStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    fun setData(stationList: List<Station>) {
        this.stationList = stationList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stationList[position]
        holder.tvStationName.text = item.name
        holder.tvUniversalSpaceTime.text = "500EUS"
        holder.ivFav.setOnClickListener {
            listener.updateStation(item,holder.ivFav)
        }
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    interface FavSpaceStationsAdapterListener {
        fun updateStation(station: Station, ivStar: ImageView)
    }
}