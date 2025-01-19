package com.example.hillsloungeapplication.Places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.R
import org.w3c.dom.Text

class PlaceAdapter(
    private var places: List<Place>,): RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_title_place_rv_card_view)
        val addressText: TextView = itemView.findViewById(R.id.tv_address_place_rv_card_view)
        val image: ImageView = itemView.findViewById(R.id.iv_place_rv_card_view)
        val timeText: TextView = itemView.findViewById(R.id.tv_time_place_rv_card_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_rv_card_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place = places[position]
        holder.titleText.text = place.title
        holder.addressText.text = place.address
        Glide.with(holder.itemView.context).load(place.imageView).into(holder.image)
        holder.timeText.text = place.timeDescription
    }

    override fun getItemCount() = places.size

    fun updateData(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}