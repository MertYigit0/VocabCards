package com.mertyigit0.vocabcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mertyigit0.vocabcards.databinding.FragmentWordDetailBinding


class WordDetailFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailBinding
    private val args: WordDetailFragmentArgs by navArgs() // NavArgs kullanarak argümanları al

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val word = args.word
        binding.tvDetailEnglishWord.text = word.english
        binding.tvDetailTurkishWord.text = word.turkish
    }
}
