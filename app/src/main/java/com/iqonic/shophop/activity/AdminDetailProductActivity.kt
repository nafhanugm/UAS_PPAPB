package com.iqonic.shophop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iqonic.shophop.databinding.ActivityProductDetailAdminBinding
import com.iqonic.shophop.models.ProductModel

class AdminDetailProductActivity: AppCompatActivity() {
    private lateinit var  binding: ActivityProductDetailAdminBinding

    companion object {
        val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the product from the intent
        val product = intent.getSerializableExtra(EXTRA_PRODUCT) as ProductModel

        with(binding){
            // Set the product details
            tvDetailProductName.text = product.name
            tvDetailProductPrice.text = product.price
            tvDetailProductDescription.text = product.description

            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}