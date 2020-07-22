package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/*获取实时天气消息的数据模型*/

data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    //skycon(sky condition)：天空(气)状况
    data class Realtime(val skycon: String, val temperature: Float,
                        @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI)     //aqi(air quality index)：空气质量指数

    class AQI(val  chn: Float)     //chn：中国标准的AQI
}