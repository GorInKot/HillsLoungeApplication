package com.example.hillsloungeapplication.Places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.Home.recyclerView.BigRV.Card
import com.example.hillsloungeapplication.R
import org.w3c.dom.Text

class PlaceAdapter(
    private var places: List<Place>,
    private val onCardClick: (Place) -> Unit
): RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {
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
        Glide.with(holder.itemView.context)
            .load(place.imageView)
            .override(960, 720) // Принудительно устанавливаем одинаковый размер
            .centerCrop() // Обрезка по центру, чтобы избежать искажений
            .into(holder.image)
        holder.timeText.text = place.timeDescription

        holder.itemView.setOnClickListener {
            onCardClick(place)
        }
    }

    override fun getItemCount() = places.size

    fun updateData(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}