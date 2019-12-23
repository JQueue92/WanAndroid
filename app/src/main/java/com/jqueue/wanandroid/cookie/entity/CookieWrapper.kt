package com.jqueue.wanandroid.cookie.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.Cookie

@Entity(tableName = "Cookie")
data class CookieWrapper(
    @ColumnInfo(name="id") @PrimaryKey val id: String,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="value") val value: String,
    @ColumnInfo(name="expiresAt") val expiresAt: Long,
    @ColumnInfo(name="domain") val domain: String,
    @ColumnInfo(name="path") val path: String,
    @ColumnInfo(name="secure") val secure: Boolean,
    @ColumnInfo(name="httpOnly") val httpOnly: Boolean,
    @ColumnInfo(name="persistent") val persistent: Boolean,
    @ColumnInfo(name="hostOnly") val hostOnly: Boolean,
    @ColumnInfo(name="host") val host: String
) {
    fun cookie(): Cookie {
        val builder =
            Cookie.Builder().name(name).value(value).expiresAt(expiresAt).domain(domain).path(path)
        when {
            secure -> {
                builder.secure()
            }
            httpOnly -> {
                builder.httpOnly()
            }
            hostOnly -> {
                builder.hostOnlyDomain(domain)
            }
        }
        return builder.build()
    }
}