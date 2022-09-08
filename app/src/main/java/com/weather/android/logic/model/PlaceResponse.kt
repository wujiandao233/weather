package com.weather.android.logic.model

import android.location.Location
import com.google.android.gms.location.places.Place
import com.google.gson.annotations.SerializedName

//import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status:String, val places:List<Place>)


data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)
//这里是对应API接口的函数places为数组名，name是城市名，lcation是坐标，formatred_address是详细地址.
//这里之所以加上@SerializedName是因为JSON与Kotlin命名规范不一致所以使用注解

data class Location(val lng:String, val lat:String)