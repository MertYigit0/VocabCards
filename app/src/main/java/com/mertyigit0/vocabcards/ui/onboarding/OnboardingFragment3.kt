package com.mertyigit0.vocabcards.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.FragmentOnboarding3Binding
import com.mertyigit0.vocabcards.ui.main.MainActivity

class OnboardingFragment3 : Fragment() {

    private var _binding: FragmentOnboarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboarding3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFinish.setOnClickListener {
            finishOnboarding() // Onboarding tamamlandığında çağır
        }
    }


    private fun finishOnboarding() {
        // Onboarding tamamlandığında SharedPreferences güncelle
        val sharedPreferences = requireActivity().getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isOnboardingCompleted", true).apply()

        // MainActivity'ye geç
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Onboarding aktivitelerini kapat
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Binding referansını temizleyin
    }
}
