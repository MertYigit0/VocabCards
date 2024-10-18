package com.mertyigit0.vocabcards

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class MyApplication : Application() {
    // DataStore tanımı
    val dataStore: DataStore<Preferences> by preferencesDataStore("app_prefs")
}
