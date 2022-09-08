package com.weather.android.logic
//新建一个Repository单例类，作为仓库层的统一封装入口
/*安卓是不允许在主线程中进行网络请求的，诸如读写数据库之类的本地数据操作也不建议在主线程中进行、
因此非常有必要在仓库层进行一次线程转换*/
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.weather.android.logic.model.Place
import com.weather.android.logic.network.SunnyWeathweNetwork
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
object Repository {
    fun searchPlaces(query: String)= liveData(Dispatchers.IO){
        //将liveData函数的线程参数类型指定成了Dispatchers.IO。所有代码都运行在子线程中
        /*这里的liveData函数是lifecycle-livedata-ktx库提供的一个功能。可以自动构建并返回一个liveData对象
        ，然后在他的代码块中提供一个挂起函数的上下文，这样就可以在liveData函数的代码块中调用任意的挂起函数了*/
        val result = try{
            val placeResponse = SunnyWeathweNetwork.searchPlaces(query)
            //这里兴建placeResponse调用SunnyWeatherNetwork的searchPlaces函数来搜索城市数据
            if (placeResponse.status == "ok"){
                //如果服务器响应的状态是ok，那么使用Result.success方法来包装获取的城市数据列表
                val places = placeResponse.places
                Result.success(places)
            } else{
                //否则使用Result.failure方法来包装一个异常信息
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception){
            Result.failure<List<Place>>(e)
        }
        //使用emit方法将包装结果发射出去
        emit(result)
    }
}