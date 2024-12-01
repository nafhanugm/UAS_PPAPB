package com.iqonic.shophop.api.services.api_interface

import com.iqonic.shophop.api.apiModel.entity.Product
import com.iqonic.shophop.api.apiModel.response.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductInterface {
    @GET("products")
    fun getProducts(): Call<ArrayList<Product>>

    @POST("products/{id}")
    fun updateProduct(@Path("id") id:String, @Body product: Product ): Call<Product>

    @POST("products")
    fun addProduct(@Body product: Product): Call<Product>

    @DELETE("products/{id}")
    fun deleteProduct(@Path("id") id: String): Call<PostResponse>
}