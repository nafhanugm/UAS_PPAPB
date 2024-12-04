package com.iqonic.phonphon_store.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqonic.phonphon_store.databinding.ItemProductAdminBinding
import com.iqonic.phonphon_store.models.ProductModel

class AdminProductAdapter(
    private val products: MutableList<ProductModel>,
    private val onItemClick: (ProductModel) -> Unit,
    private val onEditClick: (ProductModel) -> Unit,
    private val onDeleteClick: (ProductModel) -> Unit
) : RecyclerView.Adapter<AdminProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductAdminBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(product: ProductModel) {
            with(binding){
                tvProductNameAdmin.text = product.name.toString()
                tvProductPriceAdmin.text = "$${product.price}"
                tvProductDescriptionAdmin.text = product.description

                btnEditProduct.setOnClickListener {
                    onEditClick(product)
                }

                btnDeleteProduct.setOnClickListener{
                    onDeleteClick(product)
                }

                itemView.setOnClickListener {
                    onItemClick(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    fun removeProduct(product: ProductModel) {
        val position = products.indexOf(product)
        if (position != -1) {
            products.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addProduct(product: ProductModel) {
        products.add(product)
        notifyItemInserted(products.size - 1)
    }

    fun setProducts(products: ArrayList<ProductModel>) {
        Log.d("dalem", products.toString())
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun updateProducts(products: ArrayList<ProductModel>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
}