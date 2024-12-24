package com.example.hillsloungeapplication.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hillsloungeapplication.R

class NewsFieldsAdapter : RecyclerView.Adapter<NewsFieldsAdapter.ViewHolder>() {
    private val mNewsFields = arrayOf(
        "Новый год в Hills Lounge Саларьево",
        "Работаем для Вас 31 декабря с 20:00 и до утра!"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_list_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mNewsFields[position]
    }

    override fun getItemCount(): Int {
        return mNewsFields.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view_1)
    }
}
