package com.weather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL="https://api.caiyunapp.com/"
    //此处的BASE_URL用于指定Retrofit的根路径
    private val retrofit = Retrofit.Builder()//构建Retrofit对象
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    //创建create()方法，接受Class类型的参数（也就是PlaceService中的Class）
    //在外部调用这个方法时，也就是调用了Retrofit对象的create()方法，从而创建出相应Service接口的动态代理对象
    inline fun <reified T> create(): T = create(T::class.java)
}