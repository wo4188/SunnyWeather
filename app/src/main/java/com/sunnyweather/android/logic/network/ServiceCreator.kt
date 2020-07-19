package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*定义的PlaceService接口的Retrofit构建器*/

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //这里提供一个外部可见的create()方法。
    //当在外部调用这个create()方法时，
    //实际上是调用了ServiceCreator单例类内部的Retrofit对象的create()方法，
    //从而创建出相应的Service接口的动态代理对象
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

//    inline fun <reified T> create(): T = create(T::class.java)        //对前面一行代码的优化，参考p460

}