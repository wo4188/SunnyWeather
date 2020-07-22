package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyResponse(val status: String, val result: Result) {

    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>,
                     @SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(val coldRisk: List<LifeDescription>,
                         val carWashing: List<LifeDescription>,
                         val ultraviolet: List<LifeDescription>,
                         val dressing: List<LifeDescription>)     //ultraviolet紫外线

    data class LifeDescription(val desc: String)        //desc即description 描述、说明。不是“降序”的意思
}