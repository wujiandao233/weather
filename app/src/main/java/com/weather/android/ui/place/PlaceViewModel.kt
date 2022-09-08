package com.weather.android.ui.place
/*
这是ViewModel层。ViewModel相当于逻辑层和UI层之间的一个桥梁
虽然更偏向逻辑层的部分。但是由于ViewModel通常和Activity或Fragment是
一一对应的，因此我们还是习惯将他们放在一起
*/
/*
定义searchPlaces方法并没有直接调用仓库层中的searchPlaces方法，
将传入的搜索参数赋值给一个searchLiveData对象，并使用Transformations
的switchMap方法来观察这个对象，否则仓库层返回的LiveData对象暗降无法进行观察。
*/
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.weather.android.logic.Repository
import com.weather.android.logic.model.Place
import retrofit2.http.Query
import androidx.lifecycle.*

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }



}