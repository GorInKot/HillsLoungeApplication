package com.example.hillsloungeapplication.Home.recyclerView


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.R

class CustomRecyclerAdapter(private val cards: List<Card>) : RecyclerView
.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

//    private var currentCardIndex = 0
//    var cardDisplayManager: CardDisplayManager? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.idTVCourseName)
        val smallTextView: TextView = itemView.findViewById(R.id.idTVCourseRating)
        val imageView: ImageView = itemView.findViewById(R.id.idIVCourseImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
//        cardDisplayManager = CardDisplayManager()
        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card = cards[position]
        holder.largeTextView.text = card.header
        holder.smallTextView.text = card.helperText
        Glide.with(holder.itemView.context).load(card.imageUrl).into(holder.imageView)


//        if (position == currentCardIndex) {
//            cardDisplayManager!!.startDisplayingCards(cards, ::displayCard)
//        }
//    }
//    private fun displayCard(index: Int) {
//
    }

    override fun getItemCount() = cards.size
}

