package com.test.shopeeclone.data.remote.retrofit

import com.test.shopeeclone.helper.AuthPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {
    fun getApiService(authPreferences: AuthPreferences): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor {
            val request = it.request()
            val token = runBlocking {
                val tokenValue = authPreferences.getData().firstOrNull()?.token ?: ""
                tokenValue
            }
            val header = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            it.proceed(header)


        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com")
            //            .baseUrl(BuildConfig.BASE_URL)
            .build()


        return retrofit.create(ApiService::class.java)
    }
}