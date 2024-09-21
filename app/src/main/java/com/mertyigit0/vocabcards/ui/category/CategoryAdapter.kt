package com.mertyigit0.vocabcards.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Category


interface OnCategoryClickListener {
    fun onCategoryClick(categoryId: Long)
}

class CategoryAdapter(
    private val categories: List<Category>,
    private val listener: OnCategoryClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var wordCounts: Map<Long, Pair<Int, Int>> = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, wordCounts[category.id])
        holder.itemView.setOnClickListener {
            listener.onCategoryClick(category.id)
        }
    }

    override fun getItemCount() = categories.size

    fun updateWordCounts(newWordCounts: Map<Long, Pair<Int, Int>>) {
        wordCounts = newWordCounts
        notifyDataSetChanged() // Tüm öğeleri güncelle
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category, wordCount: Pair<Int, Int>?) {
            itemView.findViewById<TextView>(R.id.tvCategoryName).text = category.name
            itemView.findViewById<TextView>(R.id.tvCategoryEmoji).text = category.emoji

            val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
            val tvWordProgress = itemView.findViewById<TextView>(R.id.tvWordProgress)

            if (wordCount != null) {
                val (learned, total) = wordCount
                tvWordProgress.text = "$learned/$total"
                progressBar.max = total
                progressBar.progress = learned
                progressBar.visibility = View.VISIBLE // Görünür hale getir
            } else {
                tvWordProgress.text = "0/0"
                progressBar.progress = 0
                progressBar.visibility = View.GONE // Gizle
            }
        }
    }

}

