package com.weather.android
import android.app.Application
import android.annotation.SuppressLint
import android.content.Context

class weatherApplication : Application(){
    companion object{
        const val TOKEN="n3fdc1aSF10dNV5W"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    
    override fun onCreate(){
        super.onCreate()
        context = applicationContext
    }
}