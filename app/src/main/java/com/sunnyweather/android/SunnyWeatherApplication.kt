package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    companion object {

        //@SuppressLint标注，作用：忽略指定的警告
        //(这里是忽略了内存泄漏问题，该处实际上是不存在该风险的，所以忽略了可能出现的警告提示)

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "RRGuQlM2foeMteyq"            //申请到的令牌值
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext        //即getApplicationContext()方法，返回一个Context上下文对象
    }
}