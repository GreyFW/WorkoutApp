package com.example.workouttracker.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.workouttracker.data.lab_sync.NotificationHelper

class WorkoutContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        // Инициализация базы данных (Room)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        // Здесь должен быть вызов DAO, возвращающий Cursor
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // Требование из лаб 4: Уведомление о добавлении задачи из другого приложения
        context?.let { ctx ->
            NotificationHelper.showNotification(
                ctx,
                "Новая заметка",
                "Заметка добавлена сторонним приложением"
            )
        }
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
}