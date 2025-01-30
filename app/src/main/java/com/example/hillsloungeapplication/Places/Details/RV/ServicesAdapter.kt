package com.example.hillsloungeapplication.Places.Details.RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.R

class ServicesAdapter(
    private var servicesCards: List<ServicesCard>
):RecyclerView.Adapter<ServicesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.placeDetail_rv_services_item)
        val textView: TextView = itemView.findViewById(R.id.tv_places_detail_rv_services_cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.places_detail_rv_services_item, parent, false)
        return MyViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageRes = servicesCards[position]
        holder.textView.text = imageRes.header
        Glide.with(holder.imageView.context).load(imageRes.imageUrl).into(holder.imageView)
    }

    override fun getItemCount() = servicesCards.size


    fun updateData(newServicesCards: List<ServicesCard>) {
        servicesCards = newServicesCards
        notifyDataSetChanged()
    }
    }
