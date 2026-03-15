package com.example.workouttracker.data.lab_sync

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class FakePostDto(
    val title: String,
    val body: String,
    val userId: Int = 1
)

interface JsonPlaceholderApi {
    @POST("/posts")
    suspend fun syncNotes(@Body post: FakePostDto): FakePostDto
}

object RetrofitClient {
    val api: JsonPlaceholderApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}