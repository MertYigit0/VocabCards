package com.mertyigit0.vocabcards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.vocabcards.databinding.ItemWordBinding

class WordAdapter(
    private val wordList: List<Word>,
    private val onItemClick: (Word) -> Unit // Tıklama olayını işlemek için bir lambda fonksiyonu
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    class WordViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordList[position]
        holder.binding.tvEnglishWord.text = word.english
        holder.binding.tvTurkishWord.text = word.turkish

        // Tıklama olayını ayarla
        holder.itemView.setOnClickListener {
            onItemClick(word)
        }
    }

    override fun getItemCount(): Int = wordList.size
}
