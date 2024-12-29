package com.test.shopeeclone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.shopeeclone.data.remote.response.ItemResponse
import com.test.shopeeclone.data.repository.StoreRepository
import com.test.shopeeclone.helper.Result

class HomeViewModel(private val storeRepository: StoreRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getShoppingItem(): LiveData<Result<List<ItemResponse>>> = storeRepository.getShoppingItem()
}