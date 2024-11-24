package com.iqonic.shophop.api.repository

import com.iqonic.shophop.api.ApiClient
import com.iqonic.shophop.api.ApiService
import com.iqonic.shophop.api.apiModel.entity.Product
import com.iqonic.shophop.api.util.ProductAdapter
import com.iqonic.shophop.models.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository() {
    companion object {
        fun getProductsFromAPI(onApiSuccess: (ArrayList<ProductModel>) -> Unit) {
            ApiClient.getInstance().getProducts().enqueue(object : Callback<ArrayList<Product>> {
                override fun onResponse(
                    call: Call<ArrayList<Product>>,
                    response: Response<ArrayList<Product>>
                ) {
                    if (response.isSuccessful) {
                        val products = response.body()?.map {
                            ProductAdapter.ProductToProductModel(it)
                        } ?: arrayListOf()

                        onApiSuccess(ArrayList(products))
                    }
                }

                override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                    // Handle error case
                    t.printStackTrace()
                }
            })
        }
    }

}