package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/*仓库层。有点像是一个数据获取与缓存的中间层。判断调用方请求的数据应该从本地还是网络数据源中，获取并返回数据。*/

object Repository {

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
}