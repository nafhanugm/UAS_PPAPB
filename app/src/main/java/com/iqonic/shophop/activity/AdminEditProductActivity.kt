package com.iqonic.shophop.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.iqonic.shophop.api.repository.ProductRepository
import com.iqonic.shophop.databinding.ActivityEditProductBinding
import com.iqonic.shophop.models.ProductModel

class AdminEditProductActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditProductBinding
    companion object {
        val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var product = intent.getSerializableExtra(EXTRA_PRODUCT) as ProductModel
        with(binding){
            edtEditProductName.setText(product.name)
            edtEditProductPrice.setText(product.price)
            edtEditProductDescription.setText(product.description)


            btnSaveProduct.setOnClickListener {
                // Save the product
                btnSaveProduct.text = "Loading..."
                btnSaveProduct.isEnabled = false

                product.name = edtEditProductName.text.toString()
                product.price = edtEditProductPrice.text.toString()
                product.description = edtEditProductDescription.text.toString()
                ProductRepository.updateProduct(product, {
                    finish()
                })
            }
        }


    }
}