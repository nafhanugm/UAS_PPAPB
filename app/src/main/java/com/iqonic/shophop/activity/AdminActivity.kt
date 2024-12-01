package com.iqonic.shophop.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqonic.shophop.R
import com.iqonic.shophop.adapter.AdminProductAdapter
import com.iqonic.shophop.api.repository.ProductRepository
import com.iqonic.shophop.databinding.ActivityAdminBinding
import com.iqonic.shophop.models.ProductModel
import com.iqonic.shophop.utils.extensions.listAllProducts
import com.iqonic.shophop.utils.extensions.productsFromAssets
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.android.synthetic.main.activity_admin.btnAddProduct

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var adapterRv: AdminProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsFromAssets {
            binding.recyclerViewProducts.apply {
                layoutManager = LinearLayoutManager(this@AdminActivity)
                adapter = AdminProductAdapter(
                    products = it,
                    onItemClick = { product -> ItemClick(product) },
                    onEditClick = { product ->
                        EditClick(product)
                    },
                    onDeleteClick = { product ->
                        deleteClick(product)
                    }

                )

            }
            // Show skeleton loading initially
            binding.recyclerViewProducts.loadSkeleton(R.layout.item_product_admin) {
                // Optional: customize skeleton loading appearance
                color(R.color.skeleton_light_color)
                shimmer(true)
                itemCount(10)
            }
        }

        listAllProducts { productList ->
            // Hide skeleton once data is loaded
            binding.recyclerViewProducts.hideSkeleton()

            adapterRv = AdminProductAdapter(
                products = ArrayList(productList.slice(1 until productList.size)),
                onItemClick = { product -> ItemClick(product) },
                onEditClick = { product -> EditClick(product)  },
                onDeleteClick = { product -> deleteClick(product)  }
            )

            val productExample = productList[2]
            btnAddProduct.setOnClickListener {
                addClick(productExample)
            }

            binding.recyclerViewProducts.apply {
                layoutManager = LinearLayoutManager(this@AdminActivity)
                adapter = adapterRv
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        binding.recyclerViewProducts.loadSkeleton(R.layout.item_product_admin) {
            color(R.color.skeleton_light_color)
            shimmer(true)
            itemCount(10)
        }
        if(hasFocus){
            updateRV()
        }
    }

    private fun EditClick(product: ProductModel) {
        val intent = Intent(this, AdminEditProductActivity::class.java)
        intent.putExtra(AdminEditProductActivity.EXTRA_PRODUCT, product)
        startActivity(intent)
    }

    private fun ItemClick(product: ProductModel) {
        val intent = Intent(this, AdminDetailProductActivity::class.java)
        intent.putExtra(AdminDetailProductActivity.EXTRA_PRODUCT, product)
        startActivity(intent)
    }

    private fun addClick(product: ProductModel){
        val intent = Intent(this, AdminAddProductActivity::class.java)
        intent.putExtra(AdminAddProductActivity.EXTRA_PRODUCT, product)
        startActivity(intent)
    }

    private fun deleteClick(product: ProductModel){
        binding.recyclerViewProducts.loadSkeleton(R.layout.item_product_admin) {
            color(R.color.skeleton_light_color)
            shimmer(true)
            itemCount(10)
        }
        ProductRepository.deleteProduct(product, {
            updateRV()
        })
    }

    private fun updateRV(){

        listAllProducts { productList ->
            // Hide skeleton once data is loaded
            binding.recyclerViewProducts.hideSkeleton()

            adapterRv = AdminProductAdapter(
                products = ArrayList(productList.slice(1 until productList.size)),
                onItemClick = { product -> ItemClick(product) },
                onEditClick = { product -> EditClick(product)  },
                onDeleteClick = { product -> deleteClick(product)  }
            )

            binding.recyclerViewProducts.apply {
                layoutManager = LinearLayoutManager(this@AdminActivity)
                adapter = adapterRv
            }
        }
    }
}