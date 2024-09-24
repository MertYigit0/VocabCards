package com.mertyigit0.vocabcards.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.workmanager.SyncDataWorker
import com.mertyigit0.vocabcards.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WorkManager'ı başlat
        SyncDataWorker.scheduleSyncDataWork(applicationContext)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightorange)

        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language_code", "en")
        languageCode?.let { setLocale(it) }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState == null) {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setSupportActionBar(findViewById(R.id.toolbar))
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
            binding.bottomNavigationView.setupWithNavController(navController)
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

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







        // Android 13 ve üzeri cihazlar için POST_NOTIFICATIONS iznini kontrol et
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        } else {
            // Daha düşük sürümlerde izne gerek yok, işlemleri yap
            startSyncDataWork()
        }
    }

    // İzin sonuçlarını dinlemek için launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // İzin verildi, WorkManager işlemini başlat
            startSyncDataWork()
        } else {
            // İzin reddedildi
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

        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("language_code", languageCode)
            apply()
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }


    private fun checkNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // İzin zaten verilmiş, WorkManager işlemini başlat
                startSyncDataWork()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                // Kullanıcı izni reddetmiş ancak tekrar sorabilirsiniz
                Toast.makeText(this, "Bildirim izni gerekli.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // İzin isteme
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun startSyncDataWork() {
        // Burada WorkManager işlerinizi başlatabilirsiniz
        SyncDataWorker.scheduleSyncDataWork(applicationContext)
    }

}

