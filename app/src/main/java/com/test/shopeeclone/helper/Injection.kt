package com.test.shopeeclone.helper

import android.content.Context
import com.test.shopeeclone.data.remote.retrofit.ApiConfig
import com.test.shopeeclone.data.repository.StoreRepository

object Injection {
    fun provideRepository(context: Context): StoreRepository {
        val authPreferences = AuthPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(authPreferences)
        return StoreRepository.getInstance(apiService, authPreferences)
    }
}