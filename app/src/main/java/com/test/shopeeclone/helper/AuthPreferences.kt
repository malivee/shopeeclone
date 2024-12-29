package com.test.shopeeclone.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.test.shopeeclone.data.model.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
class AuthPreferences private constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveData(dataModel: DataModel) {
        dataStore.edit {
            it[TOKEN] = dataModel.token
            it[IS_LOGIN] = true
        }
    }

    fun getData(): Flow<DataModel> {
        return dataStore.data.map {
            DataModel(
                it[TOKEN] ?: "",
                it[IS_LOGIN] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val IS_LOGIN = booleanPreferencesKey("isLogin")

        @Volatile
        private var INSTANCE: AuthPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences =
            INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
    }
}