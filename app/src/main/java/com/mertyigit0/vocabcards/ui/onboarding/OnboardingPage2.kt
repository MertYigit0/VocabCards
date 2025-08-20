package com.mertyigit0.vocabcards.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mertyigit0.vocabcards.R

@Composable
fun OnboardingPage2() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center, // dikey ortala
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboardingimage2),
            contentDescription = "Onboarding Image 2",
            modifier = Modifier
                .height(350.dp)
                .padding(bottom = 50.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.explore_features),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = stringResource(id = R.string.with_vocabcards_you_can_track_your_progress_and_review_learned_words_easily_make_your_learning_experience_unique_and_tailored_to_your_needs),
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

