package com.mertyigit0.vocabcards.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightorange)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Toolbar'ı ActionBar olarak ayarla

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Setup Bottom Navigation with NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Optionally, handle navigation to a specific destination when Bottom Navigation item is selected
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.wordListFragment -> {
                    navController.navigate(R.id.wordListFragment)
                    true
                }
                R.id.learnedListFragment -> {
                    navController.navigate(R.id.learnedListFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_language -> {
                // Dil seçme ekranını aç
                showLanguageSelectionDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLanguageSelectionDialog() {
        val languages = resources.getStringArray(R.array.language_array)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Language")
        builder.setItems(languages) { dialog, which ->
            // `which` kullanıcının seçtiği dilin indeksidir
            when (which) {
                0 -> setLocale("en")
                1 -> setLocale("tr")
                2 -> setLocale("de")
                3 -> setLocale("it")
                4 -> setLocale("es")
                5 -> setLocale("fr")
            }
        }
        builder.show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        recreate() // Aktiviteyi yeniden oluşturun
    }



}

