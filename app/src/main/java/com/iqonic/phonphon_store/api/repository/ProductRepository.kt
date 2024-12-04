package com.iqonic.phonphon_store.api.repository

import android.util.Log
import com.iqonic.phonphon_store.api.ApiClient
import com.iqonic.phonphon_store.api.apiModel.entity.Product
import com.iqonic.phonphon_store.api.apiModel.response.PostResponse
import com.iqonic.phonphon_store.api.util.ProductAdapter
import com.iqonic.phonphon_store.models.ProductModel
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
                        Log.d("ProductRepository", "Body: ${response.body()}")

                        onApiSuccess(ArrayList(products))
                    }
                }

                override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                    // Handle error case
                    t.printStackTrace()
                }
            })
        }

        fun updateProduct(product: ProductModel, onApiSuccess: (ProductModel) -> Unit) {
            ApiClient.getInstance().updateProduct(product.id ?: "", ProductAdapter.ProductModelToProduct(product)).enqueue(object : Callback<Product> {
                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    if (response.isSuccessful) {
                        val productResult = response.body()?.let {
                            ProductAdapter.ProductToProductModel(it)
                        }
                        Log.d("ProductRepository", "Body: ${response.body()}")

                        productResult?.let {
                            onApiSuccess(it)
                        }
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    // Handle error case
                    t.printStackTrace()
                }
            })
        }

        fun addProduct(product: ProductModel, onApiSuccess: (ProductModel) -> Unit) {
            ApiClient.getInstance().addProduct(ProductAdapter.ProductModelToProduct(product)).enqueue(object : Callback<Product> {
                override fun onResponse(
                    call: Call<Product>,
                    response: Response<Product>
                ) {
                    if (response.isSuccessful) {
                        val productResult = response.body()?.let {
                            ProductAdapter.ProductToProductModel(it)
                        }
                        Log.d("ProductRepository", "Body: ${response.body()}")

                        productResult?.let {
                            onApiSuccess(it)
                        }
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    // Handle error case
                    t.printStackTrace()
                }
            })
        }

        fun deleteProduct(product: ProductModel, onApiSuccess: () -> Unit) {
            ApiClient.getInstance().deleteProduct(product.id ?: "").enqueue(object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    if (response.isSuccessful) {
                        onApiSuccess()
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    // Handle error case
                    t.printStackTrace()
                }
            })
        }
    }

}