package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

object PlaceDao {

    //将Place对象存储到SharedPreferences文件中
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            //通过GSON将Place对象转换成一个JSON字符串后，再保存
            putString("place", Gson().toJson(place))
        }
    }

    //先将JSON字符串从SharedPreferences文件中读取出来，
    //再通过GSON将JSON字符串解析成Place对象并返回
    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")

        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context
        .getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}