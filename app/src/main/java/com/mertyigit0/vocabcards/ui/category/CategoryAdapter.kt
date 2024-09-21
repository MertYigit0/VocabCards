package com.mertyigit0.vocabcards.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Category


interface OnCategoryClickListener {
    fun onCategoryClick(categoryId: Long)
}

class CategoryAdapter(private val categories: List<Category>, private val listener: OnCategoryClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
        holder.itemView.setOnClickListener {
            listener.onCategoryClick(category.id) // Tıklama olayını dinle
        }
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) {
            itemView.findViewById<TextView>(R.id.tvCategoryName).text = category.name
            itemView.findViewById<TextView>(R.id.tvCategoryEmoji).text = category.emoji
        }
    }
}
