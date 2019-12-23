package com.jqueue.wanandroid.bean

data class BannerBean(
    val `data`: List<BannerPic>,
    val errorCode: Int,
    val errorMsg: String
)

data class BannerPic(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)