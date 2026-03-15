package com.example.workouttracker.data.lab_sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncTasksWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val fakeData = FakePostDto("Workout Sync", "Syncing notes to server")
                RetrofitClient.api.syncNotes(fakeData)

                // Сохраняем время последней синхронизации
                applicationContext
                    .getSharedPreferences("sync_prefs", Context.MODE_PRIVATE)
                    .edit()
                    .putLong("last_sync_time", System.currentTimeMillis())
                    .apply()

                NotificationHelper.showNotification(
                    applicationContext,
                    "Синхронизация завершена",
                    "Данные успешно отправлены в облако"
                )

                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }
}