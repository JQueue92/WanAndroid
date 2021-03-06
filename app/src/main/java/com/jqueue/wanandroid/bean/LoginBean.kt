package com.jqueue.wanandroid.bean

data class LoginBean(
    val `data`: UserInfo,
    val errorCode: Int,
    val errorMsg: String
)

data class UserInfo(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)