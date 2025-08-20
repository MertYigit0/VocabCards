package com.mertyigit0.vocabcards.ui.onboarding



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.mertyigit0.vocabcards.R


@Composable
fun OnboardingPage1() {
    // Scrollable Column
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp), // XML’deki marginStart/End yerine
        verticalArrangement = Arrangement.Center, // dikey ortala
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image
        Image(
            painter = painterResource(id = R.drawable.onboardingimage1),
            contentDescription = "Onboarding Image 1",
            modifier = Modifier
                .height(350.dp)
                .padding(bottom = 50.dp),
            contentScale = ContentScale.Crop
        )

        // Title Text
        Text(
            text = stringResource(id = R.string.welcome_to_vocabcards),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // Description Text
        Text(
            text = stringResource(id = R.string.discover_a_fun_and_interactive_way_to_learn_new_words_and_expand_your_vocabulary_let_s_get_started_on_your_language_journey),
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}



@Preview(showBackground = true, showSystemUi = true, widthDp = 360, heightDp = 640)
@Composable
private fun OnboardingPage1Preview() {
    MaterialTheme {
        OnboardingPage1()
    }
}