package com.jqueue.wanandroid.cookie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jqueue.wanandroid.cookie.dao.CookieDao
import com.jqueue.wanandroid.cookie.entity.CookieWrapper

@Database(entities = [CookieWrapper::class], version = 1)
abstract class WanDatabase : RoomDatabase() {
    abstract fun cookieDao(): CookieDao

    companion object {
        @Volatile
        private var database: WanDatabase? = null

        fun getDatabase(context: Context): WanDatabase {
            val temp = database
            if (temp != null) return temp
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, WanDatabase::class.java, "wan").build()
                database = instance
                return instance
            }
        }
    }
}