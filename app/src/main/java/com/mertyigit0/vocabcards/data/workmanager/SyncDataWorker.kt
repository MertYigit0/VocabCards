package com.mertyigit0.vocabcards.data.workmanager

import android.content.Context
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
        // Repository'ye erişim
        val repository = WordRepository(applicationContext)

        return try {
            // Firestore'dan verileri çek
            repository.loadWordsFromFirestore()
            repository.loadCategoriesFromFirestore()

            // Başarıyla tamamlandığını bildir
            Result.success()
        } catch (e: Exception) {
            // Hata durumunda başarısız bildirimi
            Result.failure()
        }
    }

    companion object {
        fun scheduleSyncDataWork(context: Context) {
            val currentTime = Calendar.getInstance()

            // Zamanlamak istediğiniz saat (örn. sabah 04:00)
            val targetTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 21)
                set(Calendar.MINUTE, 56)
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
