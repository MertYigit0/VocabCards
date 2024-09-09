package com.mertyigit0.vocabcards

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mertyigit0.vocabcards.databinding.FragmentWordDetailBinding
import java.io.IOException

class WordDetailFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailBinding
    private val args: WordDetailFragmentArgs by navArgs()
    private lateinit var word: Word
    private lateinit var viewModel: WordDetailViewModel
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WordDetailViewModel::class.java)
        word = args.word

        // Show ProgressBar and hide content initially
        binding.loadingContainer.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE

        // Setup UI
        binding.tvDetailEnglishWord.text = word.english
        binding.tvDetailTurkishWord.text = word.turkish

        // Observe learned status
        viewModel.isLearned.observe(viewLifecycleOwner) { isLearned ->
            updateButton(isLearned)
        }

        // Check if the word is learned
        viewModel.checkIfWordIsLearned(word)

        // Trigger fetching word details from the API
        viewModel.fetchWordDetails(word.english)

        // Observe word details from the API
        viewModel.wordDetail.observe(viewLifecycleOwner) { wordResponse ->
            wordResponse?.let {
                // Hide ProgressBar and show content when data is loaded
                binding.loadingContainer.visibility = View.GONE
                binding.contentContainer.visibility = View.VISIBLE

                binding.tvWord.text = it.word
                binding.tvPhonetic.text = it.phonetic
                binding.tvDefinitions.text = it.meanings.joinToString("\n") { meaning ->
                    meaning.partOfSpeech + ": " + meaning.definitions.joinToString(", ") { def ->
                        def.definition
                    }
                }

                // Set up the button to play audio
                val audioUrl = it.phonetics.firstOrNull()?.audio
                if (audioUrl != null) {
                    binding.btnPlayAudio.setOnClickListener {
                        viewModel.playAudio(audioUrl)
                    }
                } else {
                    binding.btnPlayAudio.visibility = View.GONE
                }
            }
        }

        binding.learnedButton.setOnClickListener {
            viewModel.toggleWordLearningStatus(word)
            // Navigate based on updated status
            val action = if (viewModel.isLearned.value == true) {
                WordDetailFragmentDirections.actionWordDetailFragmentToLearnedListFragment()
            } else {
                WordDetailFragmentDirections.actionWordDetailFragmentToWordListFragment()
            }
            findNavController().navigate(action)
        }
    }

    private fun updateButton(isLearned: Boolean) {
        binding.learnedButton.text = if (isLearned) "Unlearn" else "Learn"
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release media player when the fragment is destroyed
    }
}










