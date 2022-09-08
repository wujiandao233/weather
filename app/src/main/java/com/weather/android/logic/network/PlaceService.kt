package com.weather.android.logic.network
import com.weather.android.logic.model.PlaceResponse
import retrofit2.Call
import com.weather.android.weatherApplication
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${weatherApplication.TOKEN}&lang=zn_CN")
    //TOKEN是一个参数，表示令牌值。
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    /*我们在searchPlaces()方法的上面声明了一个@GET注解，这样当调用
searchPlaces()方法的时候，Retrofit就会自动发起一条GET请求，去访问@GET注解中配
置的地址。其中，搜索城市数据的API中只有query这个参数是需要动态指定的，我们使用
@Query注解的方式来进行实现，另外两个参数是不会变的，因此固定写在@GET注解中即可。
另外，searchPlaces()方法的返回值被声明成了Call<PlaceResponse>，这样Retrofit
就会将服务器返回的JSON数据自动解析成PlaceResponse对象了*/

}
