package com.iqonic.phonphon_store.db.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "wishlist")
class WishListRoom {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    var id: Int = 0
    var average_rating: String? = null
    var colors: String? = null
    var image: String? = null
    var name: String? = null
    var regular_price: String? = null
    var sale_price: String? = null
    var size: String? = null
    var product_id: String? = null
    var item_id: String? = null
}