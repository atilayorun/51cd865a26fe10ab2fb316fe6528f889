package com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.ItemFavStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import com.example.a51cd865a26fe10ab2fb316fe6528f889.util.Util

class FavSpaceStationsAdapter(listener: FavSpaceStationsAdapterListener): RecyclerView.Adapter<FavSpaceStationsAdapter.ViewHolder>(){
    var listener: FavSpaceStationsAdapterListener = listener
    private var favSpaceStationList: List<SpaceStation> = listOf()

    inner class ViewHolder(val binding: ItemFavStationBinding) : RecyclerView.ViewHolder(binding.root){
        val tvStationName: TextView = binding.tvStationName
        val tvUniversalSpaceTime: TextView = binding.tvUniversalSpaceTime
        val tvCapacityAndStock: TextView = binding.tvCapacityAndStock
        val ivFav:ImageView = binding.ivFavBlack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: ItemFavStationBinding = ItemFavStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    fun setData(spaceStationList: List<SpaceStation>) {
        this.favSpaceStationList = spaceStationList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favSpaceStationList[position]
        holder.tvStationName.text = item.name
        val distance = Util.distanceFormula(item.coordinateX,0.0,item.coordinateY,0.0)
        holder.tvUniversalSpaceTime.text = "$distance EUS"
        holder.tvCapacityAndStock.text = "${favSpaceStationList[position].capacity}/${favSpaceStationList[position].stock}"
        if (item.isFav) {
            holder.ivFav.setImageResource(R.drawable.ic_star_black)
        } else {
            holder.ivFav.setImageResource(R.drawable.ic_star_border_black)
        }
        holder.ivFav.setOnClickListener {
            listener.updateStation(item,holder.ivFav)
        }
    }

    override fun getItemCount(): Int {
        return favSpaceStationList.size
    }

    interface FavSpaceStationsAdapterListener {
        fun updateStation(spaceStation: SpaceStation, ivStar: ImageView)
    }
}