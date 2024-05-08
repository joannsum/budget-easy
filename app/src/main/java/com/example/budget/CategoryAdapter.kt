package com.example.budget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class CategoryAdapter(private var categories: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label : TextView = view.findViewById(R.id.label)
        val amount : TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val category = categories[position]
        val context = holder.amount.context

        holder.label.text = category.label
        holder.amount.text = "$%.2f".format(category.amount)
        if (holder.label.text == "Saved"){
            if (category.amount < 0){
                holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))

            }
            else{
                holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
        }
        else {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}