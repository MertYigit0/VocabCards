package com.mertyigit0.vocabcards.ui.main

//import com.mertyigit0.vocabcards.data.workmanager.SyncDataWorker
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mertyigit0.vocabcards.MyApplication
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.ActivityMainBinding
import com.mertyigit0.vocabcards.ui.onboarding.OnboardingActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // DataStore tanımı
    private val dataStore by lazy { (application as MyApplication).dataStore }

    // Dil tercihini saklamak için kullanılacak anahtar
    val LANGUAGE_KEY = stringPreferencesKey("language_code")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Onboarding kontrolü
        if (!isOnboardingCompleted()) {
            navigateToOnboarding()
            return
        }

        setupUI()

        // Android 13 ve üzeri cihazlar için bildirim izni kontrolü
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        } else {
            startSyncDataWork()
        }
    }

    private fun isOnboardingCompleted(): Boolean {
        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isOnboardingCompleted", false)
    }

    private fun navigateToOnboarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }

    private fun setupUI() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.toolbarColor)

        // DataStore'dan dil tercihini oku
        val languageCode = runBlocking {
            dataStore.data
                .map { preferences ->
                    preferences[LANGUAGE_KEY] ?: "en"
                }.first()
        }
        setLocale(languageCode)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, AppBarConfiguration(navController.graph))
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.categoryFragment -> {
                    navController.navigate(R.id.categoryFragment)
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

    private fun checkNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                startSyncDataWork()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                Toast.makeText(this, "Bildirim izni gerekli.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startSyncDataWork()
        } else {
            Toast.makeText(this, "Bildirim izni gerekli.", Toast.LENGTH_SHORT).show()
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
                showLanguagePopup()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLanguagePopup() {
        val popupMenu = PopupMenu(this, findViewById(R.id.action_language))
        popupMenu.menuInflater.inflate(R.menu.menu_language, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val languageCode = when (menuItem.itemId) {
                R.id.language_english -> "en"
                R.id.language_spanish -> "es"
                R.id.language_french -> "fr"
                R.id.language_italian -> "it"
                R.id.language_turkish -> "tr"
                else -> "en"
            }
            changeLanguage(languageCode)
            true
        }
        popupMenu.show()
    }

    private fun changeLanguage(languageCode: String) {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[LANGUAGE_KEY] = languageCode
            }
            updateLocale(languageCode)
            recreate()
        }
    }

    private fun setLocale(languageCode: String) {
        updateLocale(languageCode)
    }

    private fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun startSyncDataWork() {
        // Burada WorkManager işlerinizi başlatabilirsiniz
        // SyncDataWorker.scheduleSyncDataWork(applicationContext)
    }

}

