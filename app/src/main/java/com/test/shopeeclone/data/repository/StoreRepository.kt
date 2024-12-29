package com.test.shopeeclone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.test.shopeeclone.data.model.DataModel
import com.test.shopeeclone.data.remote.response.ItemResponse
import com.test.shopeeclone.data.remote.response.LoginResponse
import com.test.shopeeclone.data.remote.retrofit.ApiService
import com.test.shopeeclone.helper.AuthPreferences
import com.test.shopeeclone.helper.Result
import kotlinx.coroutines.flow.Flow

class StoreRepository(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {


    fun getData(): Flow<DataModel> {
        return authPreferences.getData()
    }

    suspend fun saveData(dataModel: DataModel) {
        authPreferences.saveData(dataModel)
    }

    suspend fun logout() {
        authPreferences.logout()
    }

    fun login(username: String, password: String) : LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)

        try {
            val client = apiService.login(username, password)
            emit(Result.Success(client))
        } catch (e: Exception) {
            Log.e("PostLogin", e.message.toString())
            emit(Result.Failure(e.message.toString()))
        }
    }

    fun getShoppingItem(): LiveData<Result<List<ItemResponse>>> = liveData {
        emit(Result.Loading)

        try {
            val client = apiService.getShoppingItem()
            emit(Result.Success(client))
        } catch (e: Exception) {
            Log.e("GetShoppingItem", e.message.toString())
            emit(Result.Failure(e.message.toString()))        }
    }



    companion object {
        private var INSTANCE: StoreRepository? = null
        fun getInstance(
            apiService: ApiService,
            authPreferences: AuthPreferences
        ): StoreRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StoreRepository(apiService, authPreferences)
            }.also { INSTANCE = it }
        }
    }
}