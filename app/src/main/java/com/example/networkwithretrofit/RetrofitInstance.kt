package com.example.networkwithretrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        val BASE_URL = "https://jsonplaceholder.typicode.com"
        val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            ).client(client).build()
        }
    }
}