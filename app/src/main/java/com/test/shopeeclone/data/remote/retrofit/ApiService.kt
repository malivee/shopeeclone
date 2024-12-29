package com.test.shopeeclone.data.remote.retrofit

import com.test.shopeeclone.data.remote.response.ItemResponse
import com.test.shopeeclone.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse


    @GET("products")
    suspend fun getShoppingItem(
    ): List<ItemResponse>
}