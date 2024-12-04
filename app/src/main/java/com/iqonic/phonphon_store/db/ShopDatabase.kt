package com.iqonic.phonphon_store.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.iqonic.phonphon_store.db.DAO.WishListDAO
import com.iqonic.phonphon_store.db.Entity.WishListRoom


@Database(entities = [WishListRoom::class], version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun wishlistDAO(): WishListDAO?
    companion object{
        @Volatile
        private var INSTANCE: ShopDatabase? = null
        fun getDatabase(context: Context): ShopDatabase? {
            if (INSTANCE == null) {
                synchronized(ShopDatabase::class.java) {
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        ShopDatabase::class.java, "shop_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}