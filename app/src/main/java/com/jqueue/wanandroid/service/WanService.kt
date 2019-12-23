package com.jqueue.wanandroid.service

import com.jqueue.wanandroid.bean.*
import okhttp3.Cookie
import retrofit2.http.*

interface WanService {
    @GET("/navi/json")
    suspend fun getNavi(): NaviBean

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): ArticleListBean

    @GET("/banner/json")
    suspend fun getBanner(): BannerBean

    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String): LoginBean

    /**
     * 获取个人积分及排名
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getUserCoin(): CoinBean
}