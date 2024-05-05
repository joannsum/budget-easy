package com.example.budget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(
    private var context: Context,
    private var titleList: ArrayList<String>,
    private var detailList: ArrayList<String>,
    private var dateList:ArrayList<String>
) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = titleList[position]
        holder.detail.text = detailList[position]
        holder.date.text=dateList[position]
    }
    override fun getItemCount(): Int {
        return titleList.size
    }
    inner class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<View>(R.id.item_title) as TextView
        var detail: TextView = itemView.findViewById<View>(R.id.item_detail) as TextView
        var date:TextView=itemView.findViewById<View>(R.id.item_date) as TextView
    }
}