package com.example.hillsloungeapplication.Home.recyclerView.SmallRV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.R

class CustomRecyclerViewAdapter(
    private val cards: List<Int>):RecyclerView.Adapter<CustomRecyclerViewAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.SRV_imageView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_small_rv_item, parent, false)
        return MyViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageRes = cards[position]
        holder.imageView.setImageResource(imageRes)
    }

    override fun getItemCount() = cards.size
    }

