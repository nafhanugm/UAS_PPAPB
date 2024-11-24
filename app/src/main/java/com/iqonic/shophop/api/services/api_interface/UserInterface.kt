package com.iqonic.shophop.api.services.api_interface

import com.iqonic.shophop.api.apiModel.entity.UserModel
import com.iqonic.shophop.api.apiModel.response.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserInterface {
    @GET("users")
    fun getUsers(): Call<List<UserModel>>

    @POST("users")
    fun createUser(@Body user: UserModel): Call<PostResponse>

    @POST("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: UserModel): Call<PostResponse>
}