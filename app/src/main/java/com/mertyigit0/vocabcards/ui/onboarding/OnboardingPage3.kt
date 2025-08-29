package com.mertyigit0.vocabcards.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.mertyigit0.hotelreservation.ui.theme.LightOrange
import com.mertyigit0.vocabcards.R

@Composable
fun OnboardingPage3(onFinishClick: () -> Unit) {
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
            painter = painterResource(id = R.drawable.onboardingimage3),
            contentDescription = "Onboarding Image 3",
            modifier = Modifier
                .height(350.dp)
                .padding(bottom = 50.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.ready_to_begin),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = stringResource(id = R.string.let_s_dive_in_click_below_to_start_your_first_vocabulary_lesson_and_unlock_the_world_of_words_your_journey_to_fluency_begins_now),
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { onFinishClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange// XML’deki arka plan rengi
            )
            // Compose 3 Material renkleri kullanıyoruz
        ) {
            Text(text = stringResource(id = R.string.start_learning))
        }
    }
}
