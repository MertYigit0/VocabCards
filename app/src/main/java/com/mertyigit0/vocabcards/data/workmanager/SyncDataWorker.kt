package com.mertyigit0.vocabcards.data.workmanager
/*
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
            val newWords = repository.loadWordsFromFirestore() // Firestore'dan yeni kelimeler
            val newCategories = repository.loadCategoriesFromFirestore() // Firestore'dan yeni kategoriler

            // Yeni kategori eklenmişse bildirim gönder ve kelimeler için bildirim göndermeyi atla
            if (newCategories.isNotEmpty()) {
                NotificationUtils.sendNotification(
                    applicationContext,
                    "Yeni Kategori Eklendi",
                    "Veritabanınıza yeni kategoriler eklendi!"
                )
            } else if (newWords.isNotEmpty()) {
                // Yeni kategori eklenmediyse ve sadece yeni kelime eklenmişse bildirim gönder
                NotificationUtils.sendNotification(
                    applicationContext,
                    "Yeni Kelime Eklendi",
                    "Veritabanınıza yeni kelimeler eklendi!"
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

            // Zamanlamak istediğiniz saat (örn. aksam 17:30)
            val targetTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 14)
                set(Calendar.MINUTE, 48)
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
*/