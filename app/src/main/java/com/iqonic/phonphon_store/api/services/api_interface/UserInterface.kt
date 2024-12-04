package com.iqonic.phonphon_store.api.services.api_interface

import com.iqonic.phonphon_store.api.apiModel.entity.UserModel
import com.iqonic.phonphon_store.api.apiModel.response.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserInterface {
    @GET("users")
    fun getUsers(): Call<List<UserModel>>

    @POST("users")
    fun createUser(@Body user: UserModel): Call<PostResponse>

    @POST("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: UserModel): Call<PostResponse>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<PostResponse>
}