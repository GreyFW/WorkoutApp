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
                // Имитация отправки данных на сервер
                val fakeData = FakePostDto("Workout Sync", "Syncing notes to server")
                RetrofitClient.api.syncNotes(fakeData)

                // Требование 5: Уведомление об успешной синхронизации
                NotificationHelper.showNotification(
                    applicationContext,
                    "Синхронизация завершена",
                    "Данные успешно отправлены в облако"
                )

                // Здесь можно было бы записать время синхронизации в SharedPreferences
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }
}