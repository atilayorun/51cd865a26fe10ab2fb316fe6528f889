package com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.ItemFavStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util

class FavSpaceStationsAdapter(listener: FavSpaceStationsAdapterListener): RecyclerView.Adapter<FavSpaceStationsAdapter.ViewHolder>(){
    var listener: FavSpaceStationsAdapterListener = listener
    private var favStationList: List<Station> = listOf()

    inner class ViewHolder(val binding: ItemFavStationBinding) : RecyclerView.ViewHolder(binding.root){
        val tvStationName: TextView = binding.tvStationName
        val tvUniversalSpaceTime: TextView = binding.tvUniversalSpaceTime
        val tvCapacityAndStock: TextView = binding.tvCapacityAndStock
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
        this.favStationList = stationList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favStationList[position]
        holder.tvStationName.text = item.name
        val distance = Util.distanceFormula(item.coordinateX,0.0,item.coordinateY,0.0)
        holder.tvUniversalSpaceTime.text = "$distance EUS"
        holder.tvCapacityAndStock.text = "${favStationList[position].capacity}/${favStationList[position].stock}"
        holder.ivFav.setOnClickListener {
            listener.updateStation(item,holder.ivFav)
        }
    }

    override fun getItemCount(): Int {
        return favStationList.size
    }

    interface FavSpaceStationsAdapterListener {
        fun updateStation(station: Station, ivStar: ImageView)
    }
}