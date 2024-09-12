package com.mertyigit0.vocabcards.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.ActivitySplashBinding
import com.mertyigit0.vocabcards.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    // ViewBinding instance
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get LottieAnimationView from binding
        val lottieAnimationView = binding.lottieAnimationView

        // Optionally set animation properties
        lottieAnimationView.setAnimation(R.raw.splash) // Replace with your animation file
        lottieAnimationView.playAnimation()

        // Duration of the splash screen
        val splashScreenDuration = 4000L // 4000 milliseconds = 4 seconds

        // Handler to transition to MainActivity after the splash screen duration
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, splashScreenDuration)

        // If the animation duration is longer or you want to ensure the animation finishes before starting MainActivity
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                // Start MainActivity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}
