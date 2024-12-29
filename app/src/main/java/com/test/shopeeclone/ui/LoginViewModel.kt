package com.test.shopeeclone.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.test.shopeeclone.data.model.DataModel
import com.test.shopeeclone.data.remote.response.LoginResponse
import com.test.shopeeclone.data.repository.StoreRepository
import com.test.shopeeclone.helper.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val storeRepository: StoreRepository): ViewModel() {
    fun getLoginData(): LiveData<DataModel> {
        return storeRepository.getData().asLiveData()
    }
    fun saveData(dataModel: DataModel) {
        viewModelScope.launch {
            storeRepository.saveData(dataModel)
        }
    }

    fun login(username: String, password: String): LiveData<Result<LoginResponse>> = storeRepository.login(username, password)
}