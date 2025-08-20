package com.mertyigit0.vocabcards.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.mertyigit0.vocabcards.ui.main.MainActivity
import com.mertyigit0.vocabcards.ui.onboarding.OnboardingPage1
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import androidx.core.content.edit

class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Onboarding tamamlanmış mı kontrol
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        if (prefs.getBoolean("isOnboardingCompleted", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnboardingPager(
                        onFinish = {
                            prefs.edit { putBoolean("isOnboardingCompleted", true) }
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingPager(onFinish: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val totalPages = 3

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = totalPages,
            state = pagerState,
            modifier = Modifier.weight(1f) // Pager alanını doldur
        ) { page ->
            when (page) {
                0 -> OnboardingPage1()
                1 -> OnboardingPage2()
                2 -> OnboardingPage3(onFinishClick = { onFinish() })
            }
        }

        // Dots
        DotsIndicator(totalDots = totalPages, selectedIndex = pagerState.currentPage)

    }
}


@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == selectedIndex) 16.dp else 12.dp)
                    .padding(4.dp)
                    .background(
                        color = if (index == selectedIndex) Color(0xFFED7A27) else Color.Gray,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}
