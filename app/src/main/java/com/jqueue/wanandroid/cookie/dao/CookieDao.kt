package com.jqueue.wanandroid.cookie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jqueue.wanandroid.cookie.entity.CookieWrapper

@Dao
interface CookieDao {
    @Query("Select * from Cookie where host = :host")
    fun getCookie(host: String): List<CookieWrapper>

    @Query("delete from Cookie where domain = :domain")
    fun clearCookie(domain: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCookie(list: List<CookieWrapper>)
}