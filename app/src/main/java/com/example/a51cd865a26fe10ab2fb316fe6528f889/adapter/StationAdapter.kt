package com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.ItemStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.Station
import java.util.*
import kotlin.collections.ArrayList

class StationAdapter(listener: StationAdapterListener) :
    RecyclerView.Adapter<StationAdapter.ViewHolder>() {
    var listener: StationAdapterListener = listener
    private var stationList = arrayListOf<Station>()
    private lateinit var copyStationList: ArrayList<Station>
    var position: Int = 0
    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: ItemStationBinding = ItemStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return ViewHolder(itemBinding)
    }

    fun setData(stationList: ArrayList<Station>) {
        this.stationList = stationList
        copyStationList = ArrayList()
        copyStationList.addAll(stationList)
        notifyDataSetChanged()
    }

    fun filter(charText: String) {
        val charText = charText.toLowerCase(Locale.getDefault())
        stationList.clear()
        if (charText.length === 0) {
            stationList.addAll(copyStationList)
        } else {
            for (station in copyStationList) {
                if (station.name.toLowerCase(Locale.getDefault())
                        .contains(charText.toLowerCase())
                ) {
                    stationList.addAll(listOf(station))
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stationList[position]
        holder.tvCapacityAndStock.text = "${item.capacity}/${item.stock}"
        holder.tvStationName.text = item.name
        holder.tvSpaceSuitCount.text = "${item.name}EUS"
        this.position = position
        println(position)
        if (item.isFav)
            holder.ivFav.setImageResource(R.drawable.ic_star_black)
        else
            holder.ivFav.setImageResource(R.drawable.ic_star_border_black)

        holder.btnTravel.setOnClickListener {
            if (!item.isActive) {
                listener.btnTravelSetOnClickListener(item)
                item.isActive=true
            }
            else{
                Toast.makeText(context, "Zaten bu istasyondasınız.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        holder.ivFav.setOnClickListener {
            listener.imgStarOnClickListener(
                item,
                holder.ivFav
            )
        }
    }

    override fun getItemCount(): Int = stationList.size

    inner class ViewHolder(val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCapacityAndStock: TextView = binding.tvCapacityAndStock
        val tvStationName: TextView = binding.tvStationName
        val tvSpaceSuitCount: TextView = binding.tvSpaceSuitCount
        val ivFav: ImageView = binding.ivFav
        val btnTravel: Button = binding.btnTravel
    }

    interface StationAdapterListener {
        fun imgStarOnClickListener(station: Station, ivStar: ImageView)
        fun btnTravelSetOnClickListener(station: Station)
    }
}

