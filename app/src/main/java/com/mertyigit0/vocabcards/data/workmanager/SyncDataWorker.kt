package com.mertyigit0.vocabcards.data.workmanager

import android.content.Context
import androidx.room.util.newStringBuilder
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mertyigit0.vocabcards.data.repository.WordRepository
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SyncDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Bildirim kanalını oluştur
        NotificationUtils.createNotificationChannel(applicationContext)

        val repository = WordRepository(applicationContext)

        return try {
            // Firestore'dan önceki veriler ile yeni verileri karşılaştır (örnek)
            val previousWords = repository.getAllWords() // Local'den mevcut kelimeler
            val newWords = repository.loadWordsFromFirestore() // Firestore'dan yeni kelimeler

            val previousCategories = repository.getAllCategories() // Local'den mevcut kategoriler
            val newCategories = repository.loadCategoriesFromFirestore() // Firestore'dan yeni kategoriler

            // Yeni kelime eklenmişse bildirim gönder
            if (newWords.isNotEmpty()) {
                NotificationUtils.sendNotification(
                    applicationContext,
                    "Yeni Kelime Eklendi",
                    "Veritabanınıza yeni kelimeler eklendi!"
                )
            }

            // Yeni kategori eklenmişse bildirim gönder
            if (newCategories.isNotEmpty()) {
                NotificationUtils.sendNotification(
                    applicationContext,
                    "Yeni Kategori Eklendi",
                    "Veritabanınıza yeni kategoriler eklendi!"
                )
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }



    companion object {
        fun scheduleSyncDataWork(context: Context) {
            val currentTime = Calendar.getInstance()

            // Zamanlamak istediğiniz saat (örn. sabah 04:00)
            val targetTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 21)
                set(Calendar.MINUTE, 12)
                set(Calendar.SECOND, 0)
            }

            // Eğer hedef saat geçmişse, bir sonraki güne ayarla
            if (targetTime.before(currentTime)) {
                targetTime.add(Calendar.DAY_OF_MONTH, 1)
            }

            val initialDelay = targetTime.timeInMillis - currentTime.timeInMillis

            // 24 saatte bir tekrar eden iş
            val workRequest = PeriodicWorkRequestBuilder<SyncDataWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

            // WorkManager'a iş ekleme
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "SyncDataWork",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}
