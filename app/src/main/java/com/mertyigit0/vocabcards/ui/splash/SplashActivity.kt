package com.mertyigit0.vocabcards.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.ActivitySplashBinding
import com.mertyigit0.vocabcards.ui.main.MainActivity
import com.mertyigit0.vocabcards.ui.onboarding.OnboardingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        startSplashAnimation()
    }

    // UI öğelerini ve ayarları başlatan fonksiyon
    private fun setupUI() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Lottie animasyonunu başlatan fonksiyon
    private fun startSplashAnimation() {
        val lottieAnimationView = binding.lottieAnimationView
        lottieAnimationView.setAnimation(R.raw.splash)
        lottieAnimationView.playAnimation()

        // Onboarding kontrolü
        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val isOnboardingCompleted = sharedPreferences.getBoolean("isOnboardingCompleted", false)

        // Splash ekran süresi
        val splashScreenDuration = 3000L // 3 saniye

        // Animasyon bitişi için dinleyici ekle
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                navigateToNextActivity(isOnboardingCompleted)
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        // Delay ile animasyon bitiminde geçiş yap
        CoroutineScope(Dispatchers.Main).launch {
            delay(splashScreenDuration)
            navigateToNextActivity(isOnboardingCompleted)
        }
    }

    // Bir sonraki aktiviteye geçiş yapan fonksiyon
    private fun navigateToNextActivity(isOnboardingCompleted: Boolean) {
        val intent = if (isOnboardingCompleted) {
            Intent(this@SplashActivity, MainActivity::class.java)
        } else {
            Intent(this@SplashActivity, OnboardingActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}

