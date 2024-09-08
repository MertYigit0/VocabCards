package com.mertyigit0.vocabcards

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mertyigit0.vocabcards.databinding.ActivityMainBinding

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NavController'ı al ve BottomNavigationView ile bağla
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}

