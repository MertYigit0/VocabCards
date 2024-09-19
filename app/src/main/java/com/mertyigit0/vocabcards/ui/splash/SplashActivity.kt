package com.mertyigit0.vocabcards.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.airbnb.lottie.LottieAnimationView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.ActivitySplashBinding
import com.mertyigit0.vocabcards.ui.main.MainActivity
import com.mertyigit0.vocabcards.ui.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lottieAnimationView = binding.lottieAnimationView
        lottieAnimationView.setAnimation(R.raw.splash)
        lottieAnimationView.playAnimation()

        // Kullanıcı onboarding'i daha önce tamamladı mı kontrol et
        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val isOnboardingCompleted = sharedPreferences.getBoolean("isOnboardingCompleted", false)

        // Splash ekranı süresi
        val splashScreenDuration = 3000L // 3 saniye

        // Handler ile geçiş yap
        Handler().postDelayed({
            val intent = if (isOnboardingCompleted) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, OnboardingActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, splashScreenDuration)

        // Animasyon bitiminde geçiş yapmak isterseniz:
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                val intent = if (isOnboardingCompleted) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashActivity, OnboardingActivity::class.java)
                }
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}
