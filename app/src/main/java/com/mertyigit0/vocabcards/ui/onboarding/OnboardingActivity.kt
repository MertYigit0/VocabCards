package com.mertyigit0.vocabcards.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("OnboardingActivity", "onCreate called")

        setupNightMode()
        setStatusBarColor()
        if (isOnboardingCompleted()) {
            navigateToMainActivity()
            return
        }

        setContentView(R.layout.activity_onboarding)
        initializeViews()
        setupViewPager()
        setupTabLayout()
    }

    // Gece modunu kapatır
    private fun setupNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    // Durum çubuğu rengini ayarlayan fonksiyon
    private fun setStatusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightorange)
    }

    // Onboarding işleminin tamamlanıp tamamlanmadığını kontrol eden fonksiyon
    private fun isOnboardingCompleted(): Boolean {
        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val isCompleted = sharedPreferences.getBoolean("isOnboardingCompleted", false)
        Log.d("OnboardingActivity", "Onboarding completed: $isCompleted")
        return isCompleted
    }

    // MainActivity'ye geçiş yapan fonksiyon
    private fun navigateToMainActivity() {
        Log.d("OnboardingActivity", "Onboarding already completed, navigating to MainActivity")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // View'ları initialize eden fonksiyon
    private fun initializeViews() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
    }

    // ViewPager'ı kuran fonksiyon
    private fun setupViewPager() {
        val fragments = listOf(
            OnboardingFragment1(),
            OnboardingFragment2(),
            OnboardingFragment3()
        )
        val adapter = OnboardingPagerAdapter(this, fragments)
        viewPager.adapter = adapter
    }

    // TabLayout'u ViewPager ile eşleştiren fonksiyon
    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }
}
