package com.mertyigit0.vocabcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mertyigit0.vocabcards.databinding.FragmentWordDetailBinding


class WordDetailFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailBinding
    private val args: WordDetailFragmentArgs by navArgs()
    private lateinit var word: Word

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        word = args.word
        binding.tvDetailEnglishWord.text = word.english
        binding.tvDetailTurkishWord.text = word.turkish

        // Check if the word is learned
        val isLearned = PrefsHelper.getLearnedWords(requireContext()).contains(word.english)
        updateButton(isLearned)



        binding.learnedButton.setOnClickListener {
            val isCurrentlyLearned = PrefsHelper.getLearnedWords(requireContext()).contains(word.english)
            if (isCurrentlyLearned) {
                PrefsHelper.removeLearnedWord(requireContext(), word)
                // Notify WordListFragment to add word back to the list
                findNavController().navigate(WordDetailFragmentDirections.actionWordDetailFragmentToWordListFragment())
            } else {
                PrefsHelper.addLearnedWord(requireContext(), word)
                // Notify LearnedListFragment to update the list
                findNavController().navigate(WordDetailFragmentDirections.actionWordDetailFragmentToLearnedListFragment())
            }
        }
    }

    private fun updateButton(isLearned: Boolean) {
        if (isLearned) {
            binding.learnedButton.text = "Unlearn"
        } else {
            binding.learnedButton.text = "Learn"
        }
    }
}
