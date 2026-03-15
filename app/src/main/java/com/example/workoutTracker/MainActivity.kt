package com.example.workouttracker

import android.Manifest
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workouttracker.data.lab_sync.NetworkChangeReceiver
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val networkChangeReceiver = NetworkChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)

        setupPeriodicSync()

        setContent {
            WorkoutTrackerTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { isGranted ->
                    }

                    LaunchedEffect(Unit) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                WorkoutApp()
            }
        }
    }

    private fun setupPeriodicSync() {
        // Условие: синхронизация только при наличии интернета
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Задача: каждые 15 минут
        val syncRequest = PeriodicWorkRequestBuilder<com.example.workouttracker.data.lab_sync.SyncTasksWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        // Добавляем задачу в очередь системы (KEEP - чтобы не дублировать таймеры)
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "PeriodicSync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // отключаем ресивер при закрытии приложения во избежание утечек памяти
        unregisterReceiver(networkChangeReceiver)
    }
}