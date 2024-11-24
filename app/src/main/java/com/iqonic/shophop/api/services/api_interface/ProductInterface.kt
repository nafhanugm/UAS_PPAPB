package com.iqonic.shophop.api.services.api_interface

import com.iqonic.shophop.api.apiModel.entity.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductInterface {
    @GET("products")
    fun getProducts(): Call<ArrayList<Product>>
}