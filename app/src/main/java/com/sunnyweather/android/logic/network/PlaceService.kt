package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//通常Retrofit的接口文件建议以具体的功能种类名开头，并以Service结尾，命名习惯


/*定义的用于访问彩云天气城市搜索API的Retrofit接口*/
interface PlaceService {

    //拼接之后的实际路径，如 https://api.caiyunapp.com/v2/place?query=北京&token={RRGuQlM2foeMteyq}&lang=zh_CN
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}


