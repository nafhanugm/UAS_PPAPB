package com.iqonic.phonphon_store.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iqonic.phonphon_store.api.repository.ProductRepository
import com.iqonic.phonphon_store.databinding.ActivityAddProductBinding
import com.iqonic.phonphon_store.models.ProductModel

class AdminAddProductActivity:AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding

    companion object{
        val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var product = intent.getSerializableExtra(EXTRA_PRODUCT) as ProductModel

        with(binding){
            btnSaveProduct.setOnClickListener {
                btnSaveProduct.text = "Saving..."
                btnSaveProduct.isEnabled = false

                product.name = edtEditProductName.text.toString()
                product.description = edtEditProductDescription.text.toString()
                product.price = edtEditProductPrice.text.toString()

                product.id =null

                // Add product to the database
                ProductRepository.addProduct(product){
                    // Handle success case
                    finish()
                }
            }
        }
    }
}