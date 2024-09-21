package com.mertyigit0.vocabcards.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
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
            val tvCategoryName = itemView.findViewById<TextView>(R.id.tvCategoryName)
            val tvCategoryEmoji = itemView.findViewById<TextView>(R.id.tvCategoryEmoji)
            val tvWordProgress = itemView.findViewById<TextView>(R.id.tvWordProgress)
            val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
            val cardView = itemView.findViewById<CardView>(R.id.cardview)

            tvCategoryName.text = category.name
            tvCategoryEmoji.text = category.emoji

            if (wordCount != null) {
                val (learned, total) = wordCount
                tvWordProgress.text = "$learned/$total"
                progressBar.max = total
                progressBar.progress = learned

                // Eğer tüm kelimeler öğrenildiyse
                if (learned == total && total > 0) {
                    // CardView rengini pasif bir renge çevir
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                    val congratsMessage = String.format(
                        itemView.context.getString(R.string.all_words_learned), "🎉"
                    )
                    tvWordProgress.text = congratsMessage // Mesajı ayarla
                    progressBar.visibility = View.GONE // İsteğe bağlı, progress barı gizleyebilirsin
                } else {
                    // Normal görünüm için eski rengi ayarla
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.lllorange))
                    progressBar.visibility = View.VISIBLE
                }
            } else {
                // Eğer kelime sayısı null ise default değerler
                tvWordProgress.text = "0/0"
                progressBar.progress = 0
                progressBar.visibility = View.GONE
            }
        }
    }


}

