package com.iqonic.phonphon_store.utils.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import com.iqonic.phonphon_store.ShopHopApp
import com.iqonic.phonphon_store.models.*
import com.iqonic.phonphon_store.utils.Constants
import com.iqonic.phonphon_store.utils.Constants.AssetFiles.ATTRIBUTES
import com.iqonic.phonphon_store.utils.Constants.AssetFiles.CATEGORYS
import com.iqonic.phonphon_store.utils.Constants.AssetFiles.PRODUCTS
import com.iqonic.phonphon_store.utils.Constants.AssetFiles.REVIEWS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iqonic.phonphon_store.db.DAO.WishListDAO
import com.iqonic.phonphon_store.db.Entity.WishListRoom
import com.iqonic.phonphon_store.db.ShopDatabase
import org.json.JSONObject
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

fun productsFromAssets(onApiSuccess: (ArrayList<ProductModel>) -> Unit) =
    onApiSuccess(
        Gson().fromJson(
            getJsonString(PRODUCTS),
            object : TypeToken<ArrayList<ProductModel>>() {}.type
        )
    )

fun categoryFromAssets(onApiSuccess: (ArrayList<CategoryData>) -> Unit) =
    onApiSuccess(
        Gson().fromJson(
            getJsonString(CATEGORYS),
            object : TypeToken<ArrayList<CategoryData>>() {}.type
        )
    )

fun getProductsReviews(
    productId: String?,
    onApiSuccess: (ArrayList<ProductReviewData>) -> Unit,
    userReviewed: (Boolean) -> Unit
) {
    val list = Gson().fromJson<ArrayList<ProductReviewData>>(
        getJsonString(REVIEWS),
        object : TypeToken<ArrayList<ProductReviewData>>() {}.type
    )
    list.reverse()
    val userReview = getUserReview(productId)
    userReviewed(userReview != null)
    if (userReview != null) {
        list.add(userReview)
    }
    list.reverse()
    onApiSuccess(list)
}

fun addUserReview(reviewData: RequestModel, onApiSuccess: (Boolean) -> Unit) {
    val list = getUserReviews()
    if (isExist(reviewData.product_id!!, list)) {
        list.forEachIndexed { index, productReviewData ->
            if (productReviewData.product_id == reviewData.product_id) {
                list[index].rating = reviewData.rating!!.toInt()
                list[index].review = reviewData.review!!

            }
        }
    } else {
        val productReviewData = ProductReviewData()
        productReviewData.email = reviewData.reviewer_email!!
        productReviewData.name = reviewData.reviewer!!
        productReviewData.review = reviewData.review!!
        productReviewData.rating = reviewData.rating!!.toInt()
        productReviewData.product_id = reviewData.product_id!!.toInt().toString()
        productReviewData.date_created = Constants.FULL_DATE_FORMATTER.format(Date())
        list.add(productReviewData)
    }
    getSharedPrefInstance().setValue(Constants.SharedPref.USER_REVIEWS, Gson().toJson(list))
    onApiSuccess(true)
}

fun deleteUserReview(reviewData: ProductReviewData, onApiSuccess: (Boolean) -> Unit) {
    val list = getUserReviews()
    if (isExist(reviewData.product_id, list)) {
        list.forEachIndexed { index, productReviewData ->
            if (productReviewData.product_id == reviewData.product_id) {
                list.removeAt(index)
            }
        }
        getSharedPrefInstance().setValue(Constants.SharedPref.USER_REVIEWS, Gson().toJson(list))
    }
    onApiSuccess(true)
}

fun isExist(id: String?, list: ArrayList<ProductReviewData>): Boolean {
    list.forEachIndexed { _, productReviewData ->
        if (productReviewData.product_id == id) {
            return true
        }
    }
    return false
}

fun getUserReviews(): ArrayList<ProductReviewData> {
    return if (getSharedPrefInstance().getStringValue(Constants.SharedPref.USER_REVIEWS).isEmpty()) {
        ArrayList()
    } else {
        Gson().fromJson(
            getSharedPrefInstance().getStringValue(Constants.SharedPref.USER_REVIEWS, ""),
            object : TypeToken<ArrayList<ProductReviewData>>() {}.type
        )
    }
}

fun getUserReview(id: String?): ProductReviewData? {
    getUserReviews().forEach { productReviewData ->
        if (productReviewData.product_id == id) {
            return productReviewData
        }
    }
    return null
}

fun getJsonString(file: String): String =
    ShopHopApp.getAppInstance().assets.open(file).bufferedReader().use {
        it.readText()
    }

fun getWishlist(context: Context?=null): ArrayList<WishList> {

    if (context != null) {
        lateinit var mWishListDAO: WishListDAO
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()
        val db = ShopDatabase.getDatabase(context)
        mWishListDAO = db!!.wishlistDAO()!!

        val list = ArrayList<WishList>()

        executorService.execute {
            mWishListDAO.allWishList.forEach {
                Log.e("WishList", it.name.toString())
                val wishList = WishList()
                wishList.average_rating = it.average_rating
                wishList.colors = Gson().fromJson(it.colors, object : TypeToken<ArrayList<String>>() {}.type)
                wishList.image = it.image
                wishList.name = it.name
                wishList.regular_price = it.regular_price
                wishList.sale_price = it.sale_price
                wishList.size = Gson().fromJson(it.size, object : TypeToken<ArrayList<String>>() {}.type)
                wishList.product_id = it.product_id
                wishList.item_id = it.item_id
                list.add(wishList)
            }
        }
        
        return list
    } else {
            return if (getSharedPrefInstance().getStringValue(Constants.SharedPref.WISHLIST_DATA).isEmpty()) {
        ArrayList()
    } else {
        Gson().fromJson(
            getSharedPrefInstance().getStringValue(Constants.SharedPref.WISHLIST_DATA, ""),
            object : TypeToken<ArrayList<WishList>>() {}.type
        )
    }
    }
}

fun Activity.addToWishList(product: ProductModel, onApiSuccess: (String?) -> Unit) {
    val id = isFavourite(product.id ?: "")
    lateinit var mWishListDAO: WishListDAO
    var executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val db = ShopDatabase.getDatabase(this)
    mWishListDAO = db!!.wishlistDAO()!!

    if (id != "-1") {
        onApiSuccess(id)
    } else {
        val list = getWishlist()
        val mSize = ArrayList<String>()
        val mColor = ArrayList<String>()

        val requestModel = WishList()

        requestModel.name = product.name
        requestModel.average_rating = product.average_rating
        if (product.images.isNotEmpty()) {
            requestModel.image = product.images[0].src
        }
        requestModel.regular_price = product.regular_price
        requestModel.sale_price = product.sale_price
        requestModel.item_id = System.currentTimeMillis().toString()
        requestModel.product_id = product.id

        if (product.attributes.isNotEmpty()) {
            for (i in 0 until product.attributes.size) {
                if (product.attributes[i].name == "Size") {
                    product.attributes[i].options.forEach {
                        mSize.add(it)
                    }
                    requestModel.size = mSize
                }

                if (product.attributes[i].name == "Color") {
                    product.attributes[i].options.forEach {
                        mColor.add(it)
                    }

                    requestModel.colors = mColor
                }
            }
        }

        val wishListRoom = WishListRoom()
        wishListRoom.average_rating = product.average_rating
        wishListRoom.colors = Gson().toJson(mColor)
        wishListRoom.image = requestModel.image
        wishListRoom.name = requestModel.name
        wishListRoom.regular_price = requestModel.regular_price
        wishListRoom.sale_price = requestModel.sale_price
        wishListRoom.size = Gson().toJson(mSize)
        wishListRoom.product_id = requestModel.product_id
        wishListRoom.item_id = requestModel.item_id

        executorService.execute { mWishListDAO.insertWishList(wishListRoom) }
        list.add(requestModel)
        updateWishList(list)
        snackBar("Successfully added")
        onApiSuccess(requestModel.item_id)
    }

}

fun Activity.updateWishList(list: ArrayList<WishList>) {
    getSharedPrefInstance().setValue(Constants.SharedPref.WISHLIST_DATA, Gson().toJson(list))
    getSharedPrefInstance().setValue(Constants.SharedPref.KEY_WISHLIST_COUNT, list.size)
    sendWishlistBroadcast()
}

fun Activity.removeWishList(aItemId: String?, onApiSuccess: (ArrayList<WishList>) -> Unit) {
    val list = getWishlist()
    lateinit var mWishListDAO: WishListDAO
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val db = ShopDatabase.getDatabase(this)
    mWishListDAO = db!!.wishlistDAO()!!

    getWishlist().forEachIndexed { index, productReviewData ->
        if (productReviewData.item_id == aItemId) {
            list.removeAt(index)
        }
    }

    executorService.execute{
        mWishListDAO.deleteWishList(mWishListDAO.getWishListByItemId(aItemId))
    }
    updateWishList(list)
    onApiSuccess(list)
}

fun isFavourite(id: String): String? {
    getWishlist().forEach { wishList ->
        if (wishList.product_id == id) {
            return wishList.item_id
        }
    }
    return "-1"
}

fun getOrders(): ArrayList<MyOrderData> {
    return if (getSharedPrefInstance().getStringValue(Constants.SharedPref.KEY_ORDERS).isEmpty()) {
        ArrayList()
    } else {
        Gson().fromJson(
            getSharedPrefInstance().getStringValue(Constants.SharedPref.KEY_ORDERS, ""),
            object : TypeToken<ArrayList<MyOrderData>>() {}.type
        )
    }
}

fun Activity.addOrder(myOrderData: MyOrderData) {
    val list = getOrders()
    myOrderData.id = list.size
    list.add(myOrderData)
    getSharedPrefInstance().setValue(Constants.SharedPref.KEY_ORDERS, Gson().toJson(list))
    getSharedPrefInstance().setValue(Constants.SharedPref.KEY_ORDER_COUNT, list.size)
    sendOrderCountChangeBroadcast()
}



fun attributes(): HashMap<String, ArrayList<Attribute>> {
    val json = JSONObject(getJsonString(ATTRIBUTES))
    val map = HashMap<String, ArrayList<Attribute>>()
    json.keys().forEach {
        map[it] = Gson().fromJson(
            json.getString(it),
            object : TypeToken<ArrayList<Attribute>>() {}.type
        )
    }
    return map

}
fun registerUser(){

}