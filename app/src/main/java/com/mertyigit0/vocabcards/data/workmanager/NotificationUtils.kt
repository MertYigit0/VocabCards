package com.mertyigit0.vocabcards.data.workmanager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mertyigit0.vocabcards.ui.main.MainActivity
/*
object NotificationUtils {

    private const val CHANNEL_ID = "sync_channel"
    private const val CHANNEL_NAME = "Sync Updates"
    private const val CHANNEL_DESCRIPTION = "Notifications for new words or categories"

    fun createNotificationChannel(context: Context) {
        // Sadece Android 8.0 (API 26) ve sonrası için bildirim kanalı oluştur
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }

            // NotificationManager'a kanalı kaydet
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Bildirim oluşturma fonksiyonu
    fun sendNotification(context: Context, title: String, message: String) {
        // Android 13 ve üstü cihazlar için bildirim iznini kontrol et
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                // Eğer izin verilmemişse, bildirimi gösterme
                return
            }
        }

        // Uygulama açılması gereken Intent oluştur
      val intent = Intent(context, MainActivity::class.java).apply {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}


        // PendingIntent oluştur
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // İzinler ve güncelleme bayrağı
        )

        // Bildirimi oluştur
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.btn_star_big_off)
            .setContentTitle(title)
            .setContentText(message)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // Ses
            .setVibrate(longArrayOf(0, 1000, 500, 1000)) // Titreşim
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Bildirime PendingIntent ekle
            .setAutoCancel(true) // Bildirime tıklandığında otomatik olarak kapansın

        // Bildirimi göster
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }


}
*/