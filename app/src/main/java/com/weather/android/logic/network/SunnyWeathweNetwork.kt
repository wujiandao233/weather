package com.weather.android.logic.network
//定义一个统一的网络数据源访问入口，对所有网络请求的API进行封装
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeathweNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    //使用ServiceCreator创建了一个PlaceService接口的动态代理对象
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    //定义searchPlaces函数并调用PlaceService接口定义的searchPlaces方法发起搜索城市数据请求
    //使用协程技术
    private suspend fun <T> Call<T>.await(): T {
        //使用await函数将searchPlaces函数声明为挂起函数
        /*声明一个泛型T，并将await函数定义成了Call<T>的扩展函数
        这样所有返回值是Call类型的Retrofit网络请求接口就可以直接调用await函数*/
        return suspendCoroutine { continuation ->
            //await函数中使用suspendCoroutine函数挂起当前协程
            /*由于扩展函数的原因，我们拥有了Call对象的上下文，可以直接
            调用enqueue方法让Retrofit发起网络请求，用同样的方式响应数据货
            网络请求失败的情况进行处理*/
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}