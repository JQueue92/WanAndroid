package com.jqueue.wanandroid.bean

data class CoinBean(
    val `data`: CoinInfo,
    val errorCode: Int,
    val errorMsg: String
)

data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)