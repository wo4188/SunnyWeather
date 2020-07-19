package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

/*
 当外部调用PlaceViewModel的searchPlaces()方法时，并不是直接发起任何请求或者函数调用，
 而是将外部传入的搜索参数query赋值给了searchLiveData对象，
 并使用自带的Transformations类的switch()方法来观察这个searchLiveData对象（否则从仓库层返回的LiveData对象将无法进行观察）。
 每当searhPlaces()方法被调用（即：一旦searchLiveData对象的数据发生变化)，
 那么，负责观察searchLiveData对象的switchMap()方法就会给出回应，执行其中的转换函数。
 然后，在转换函数中，我们就能调用仓库层中定义的searchPlaces()方法，发起网络请求(该方法会返回一个LiveData对象)，
 同时将仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象
 */

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()      //placeList集合，用于对界面上显示的城市数据进行缓存

    //switchMap()方法工作原理：将转换函数中返回的LiveData对象(不能被Activity等观察到的)转换成另一个可观察的LiveData对象
    //switchMap()方法接收2个参数：其一，被观察的对象；其二，是一个转换函数(该转换函数中，需要返回一个LiveData对象)
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->

        Repository.searchPlaces(query)      //当searchLiveData的数据发生变化时，会立刻调用这里(转换函数中)编写的代码
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}