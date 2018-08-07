package serio.tim.android.com.teatimer.di

import android.app.Application
import android.content.Context

class MyApp: Application() {
    companion object {
        private lateinit var instance: MyApp

        fun getInstance(): MyApp {
            return instance
        }

        fun getContext(): Context {
            return instance
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}