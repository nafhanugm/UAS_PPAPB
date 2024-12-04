package com.iqonic.phonphon_store.db.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.iqonic.phonphon_store.db.Entity.WishListRoom

@Dao
interface WishListDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWishList(wishList: WishListRoom)
    @Update
    fun updateWishList(wishList: WishListRoom)
    @Delete
    fun deleteWishList(wishList: WishListRoom)
    @get:Query("SELECT * from wishlist ORDER BY id ASC")
    val allWishList: List<WishListRoom>
    @Query("SELECT * FROM wishlist WHERE item_id = :itemId")
    fun getWishListByItemId(itemId: String?): WishListRoom
}