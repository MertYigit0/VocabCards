package com.mertyigit0.vocabcards.ui.worddetail


import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.model.WordResponse
import com.mertyigit0.vocabcards.databinding.FragmentWordDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar()
        setupViewModel()
        setupUI()
        observeLearnedStatus()
        setupLearnedButton()

        showLoadingState()
        viewModel.fetchWordDetails(word.english)

        observeWordDetails()
    }

    // ActionBar'ı ayarlayan fonksiyon
    private fun setupActionBar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.word_details)
    }

    // ViewModel'i başlatan fonksiyon
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[WordDetailViewModel::class.java]
        word = args.word
        viewModel.checkIfWordIsLearned(word)
    }

    private fun setupUI() {
        binding.tvDetailEnglishWord.text = word.english
        binding.tvDetailTurkishWord.text = word.turkish
        binding.tvEmoji.text = word.emoji ?: ""
        binding.tvGermanWord.text = word.german ?: "N/A"
        binding.tvItalianWord.text = word.italian ?: "N/A"
        binding.tvSpanishWord.text = word.spanish ?: "N/A"
        binding.tvFrenchWord.text = word.french ?: "N/A"
    }

    // Kelimenin öğrenilmiş olup olmadığını gözlemleyen fonksiyon
    private fun observeLearnedStatus() {
        viewModel.isLearned.observe(viewLifecycleOwner) { isLearned ->
            updateButton(isLearned)
        }
    }

    // Kelime detaylarını gözlemleyen fonksiyon
    private fun observeWordDetails() {
        viewModel.wordDetail.observe(viewLifecycleOwner) { wordResponse ->
            wordResponse?.let {
                hideLoadingState()
                updateWordDetails(it)
            }
        }
    }

    // Buton ve ses oynatma işlemlerini ayarlayan fonksiyon
    private fun setupLearnedButton() {
        binding.learnedButton.setOnClickListener {
            viewModel.toggleWordLearningStatus(word)
            showLearnedStatusMessage()
            navigateBasedOnLearnedStatus()
        }
    }

    // Öğrenme durumuna göre mesaj gösteren fonksiyon
    private fun showLearnedStatusMessage() {
        val message = if (viewModel.isLearned.value == true) {
            getString(R.string.word_unlearned_message)
        } else {
            getString(R.string.word_learned_message)
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // Öğrenme durumuna göre yönlendirme yapan fonksiyon
    private fun navigateBasedOnLearnedStatus() {
        val action = if (viewModel.isLearned.value == true) {
            WordDetailFragmentDirections.actionWordDetailFragmentToLearnedListFragment()
        } else {
            WordDetailFragmentDirections.actionWordDetailFragmentToWordListFragment()
        }
        findNavController().navigate(action)
    }

    // Yükleme durumunu gösteren fonksiyon
    private fun showLoadingState() {
        binding.loadingContainer.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE
    }

    // Yükleme durumunu gizleyen fonksiyon
    private fun hideLoadingState() {
        binding.loadingContainer.visibility = View.GONE
        binding.contentContainer.visibility = View.VISIBLE
    }

    // Kelime detaylarını güncelleyen fonksiyon
    private fun updateWordDetails(wordResponse: WordResponse) {
        binding.tvPhonetic.text = wordResponse.phonetic
        binding.tvDefinitions.text = ""

        val audioUrl = wordResponse.phonetics.firstOrNull()?.audio ?: ""
        setupAudioPlayer(audioUrl)

        binding.btnPlayAudio.visibility = if (audioUrl.isNotEmpty()) View.VISIBLE else View.GONE
        binding.lottiePlayAnimation.visibility = if (audioUrl.isNotEmpty()) View.VISIBLE else View.GONE
        binding.pronunciationLayout.visibility = if (audioUrl.isNotEmpty()) View.VISIBLE else View.INVISIBLE
    }

    // Ses oynatma işlemini yöneten fonksiyon
    private fun setupAudioPlayer(audioUrl: String) {
        binding.btnPlayAudio.setOnClickListener {
            viewModel.playAudio(audioUrl)
            playAnimation()
        }
    }

    // Animasyon oynatma fonksiyonu
    private fun playAnimation() {
        val lottiePlayAnimation: LottieAnimationView = binding.lottiePlayAnimation
        lottiePlayAnimation.playAnimation()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            lottiePlayAnimation.pauseAnimation()
        }
    }

    private fun updateButton(isLearned: Boolean) {
        binding.learnedButton.text = if (isLearned) {
            getString(R.string.unlearn)
        } else {
            getString(R.string.learn)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}









