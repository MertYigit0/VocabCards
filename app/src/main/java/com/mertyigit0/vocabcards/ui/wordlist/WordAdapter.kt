package com.mertyigit0.vocabcards.ui.wordlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Word

class WordAdapter(
    private var wordList: List<Word>,
    private val onItemClick: (Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.findViewById<TextView>(R.id.tvEnglishWord).text = word.english
            itemView.findViewById<TextView>(R.id.tvTurkishWord).text = word.turkish
            itemView.setOnClickListener { onItemClick(word) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    override fun getItemCount(): Int = wordList.size

    fun updateData(newWordList: List<Word>) {
        wordList = newWordList
        notifyDataSetChanged()
    }
}
