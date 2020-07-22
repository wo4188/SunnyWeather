package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.PlaceService
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/*仓库层。有点像是一个数据获取与缓存的中间层。判断调用方请求的数据应该从本地还是网络数据源中，获取并返回数据。*/

object Repository {

    //这里做了一层简易版的接口封装
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    //发送请求，获取关联的地点信息
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {

        //liveData()方法可以自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文。

        val result: Result<List<Place>> = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<com.sunnyweather.android.logic.model.Place>>(e)
        }
        emit(result)
    }

    //发送请求，获取指定地点的天气信息
    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {

        val result: Result<Weather> = try {
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }

                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather = Weather(
                        realtimeResponse.result.realtime,
                        dailyResponse.result.daily
                    )

                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                            "daily response status is ${realtimeResponse.status}"
                        )
                    )
                }
            }
        } catch (e: Exception) {
                Result.failure<Weather>(e)
        }
        emit(result)
    }

}