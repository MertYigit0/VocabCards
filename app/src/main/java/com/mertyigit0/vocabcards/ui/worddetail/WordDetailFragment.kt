package com.mertyigit0.vocabcards.ui.worddetail

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.databinding.FragmentWordDetailBinding

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


        (activity as AppCompatActivity).supportActionBar?.title = "Word Details"

        viewModel = ViewModelProvider(this).get(WordDetailViewModel::class.java)
        word = args.word

        // Show ProgressBar and hide content initially
        binding.loadingContainer.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE

        // Setup UI
        binding.tvDetailEnglishWord.text = word.english
        binding.tvDetailTurkishWord.text = word.turkish
        binding.tvEmoji.text = word.emoji ?: "" // Set emoji if available
        binding.tvGermanWord.text = word.german ?: "N/A"
        binding.tvItalianWord.text = word.italian ?: "N/A"
        binding.tvSpanishWord.text = word.spanish ?: "N/A"
        binding.tvFrenchWord.text = word.french ?: "N/A"


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

                // Set up the TextViews
                binding.tvPhonetic.text = it.phonetic
                binding.tvDefinitions.text = ""





                // Set up the button to play audio
                val audioUrl = it.phonetics.firstOrNull()?.audio ?: ""
                binding.btnPlayAudio.setOnClickListener {
                    viewModel.playAudio(audioUrl)

                    // ViewBinding veya findViewById ile Lottie ve Button'u bağlayın
                    val lottiePlayAnimation: LottieAnimationView = binding.lottiePlayAnimation

                    lottiePlayAnimation.playAnimation()

                    // 1.5 saniye sonra animasyonu durdur
                    Handler().postDelayed({
                        lottiePlayAnimation.pauseAnimation() // veya lottiePlayAnimation.cancelAnimation()
                    }, 1500) // 1500 ms = 1.5 saniye
                }

                // Eğer audioUrl null ise butonu göster ya da sakla
                binding.btnPlayAudio.visibility = if (audioUrl.isNotEmpty()) View.VISIBLE else View.GONE
                binding.lottiePlayAnimation.visibility= if (audioUrl.isNotEmpty()) View.VISIBLE else View.GONE
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










