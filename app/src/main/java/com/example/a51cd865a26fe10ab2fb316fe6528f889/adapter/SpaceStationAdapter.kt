package com.example.a51cd865a26fe10ab2fb316fe6528f889.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a51cd865a26fe10ab2fb316fe6528f889.R
import com.example.a51cd865a26fe10ab2fb316fe6528f889.databinding.ItemStationBinding
import com.example.a51cd865a26fe10ab2fb316fe6528f889.model.SpaceStation
import java.util.*
import kotlin.collections.ArrayList

class SpaceStationAdapter(listener: StationAdapterListener) :
    RecyclerView.Adapter<SpaceStationAdapter.ViewHolder>() {
    var listener: StationAdapterListener = listener
    private var stationList = arrayListOf<SpaceStation>()
    private var copySpaceStationList= arrayListOf<SpaceStation>()
    var position: Int = 0
    private lateinit var context: Context

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

    fun setData(spaceStationList: ArrayList<SpaceStation>) {
        this.stationList = spaceStationList
        copySpaceStationList = ArrayList()
        copySpaceStationList.addAll(spaceStationList)
        notifyDataSetChanged()
    }

    fun filter(charText: String) {
        val charText = charText.toLowerCase(Locale.getDefault())
        stationList.clear()
        if (charText.length === 0) {
            stationList.addAll(copySpaceStationList)
        } else {
            for (station in copySpaceStationList) {
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
        holder.tvDistanceToSpacecraft.text = "${item.distanceToSpacecraft}EUS"
        this.position = position
        if (item.isFav)
            holder.ivFav.setImageResource(R.drawable.ic_star_black)
        else
            holder.ivFav.setImageResource(R.drawable.ic_star_border_black)

        if(position == 0)
            holder.ivLeftArrow.visibility = View.INVISIBLE
        else
            holder.ivLeftArrow.visibility = View.VISIBLE

        if(position == stationList.size -1)
            holder.ivRightArrow.visibility = View.INVISIBLE
        else
            holder.ivRightArrow.visibility = View.VISIBLE

        holder.btnTravel.setOnClickListener {
            listener.btnTravelSetOnClickListener(item)
        }
        holder.ivFav.setOnClickListener {
            listener.imgStarOnClickListener(
                item,
                holder.ivFav
            )
        }
        holder.ivRightArrow.setOnClickListener {
            listener.scrollToNext(position + 1)
        }

        holder.ivLeftArrow.setOnClickListener {
            listener.scrollToPrevious(position - 1)
        }
    }

    override fun getItemCount(): Int = stationList.size

    inner class ViewHolder(binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCapacityAndStock: TextView = binding.tvCapacityAndStock
        val tvStationName: TextView = binding.tvStationName
        val tvDistanceToSpacecraft: TextView = binding.tvDistanceToSpacecraft
        val ivFav: ImageView = binding.ivFav
        val btnTravel: Button = binding.btnTravel
        val ivLeftArrow : ImageView = binding.ivLeftArrow
        val ivRightArrow : ImageView = binding.ivRightArrow
    }

    interface StationAdapterListener {
        fun imgStarOnClickListener(spaceStation: SpaceStation, ivStar: ImageView)
        fun btnTravelSetOnClickListener(spaceStation: SpaceStation)
        fun scrollToNext(position: Int)
        fun scrollToPrevious(position: Int)
    }
}

