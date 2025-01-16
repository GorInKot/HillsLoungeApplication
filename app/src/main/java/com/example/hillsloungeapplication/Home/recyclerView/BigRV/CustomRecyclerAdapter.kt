package com.example.hillsloungeapplication.Home.recyclerView.BigRV


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.R

class CustomRecyclerAdapter(
    private val cards: List<Card>,
    private val onCardClick: (Card) -> Unit // Лямбда для обработки кликов
) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.id_tv_big_rv_title)
        val smallTextView: TextView = itemView.findViewById(R.id.id_tv_big_rv_text)
        val imageView: ImageView = itemView.findViewById(R.id.id_iv_big_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card = cards[position]
        holder.largeTextView.text = card.header
        holder.smallTextView.text = card.helperText
        Glide.with(holder.itemView.context).load(card.imageUrl).into(holder.imageView)

        // TODO - кликер для неработающей хуеты!!!
        // Обработчик клика на карточку
        holder.itemView.setOnClickListener {
            onCardClick(card) // Вызов лямбды
        }
    }

    override fun getItemCount() = cards.size
}
