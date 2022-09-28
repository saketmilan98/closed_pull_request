package app.github.closedpullerq.app

import android.app.Application


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        currentApplication = this
    }

    companion object{
        private var currentApplication: MyApp? = null
        fun getInstance(): MyApp? {
            return currentApplication
        }
        val BASE_URL = "https://api.github.com/repos/saketmilan98/kotlintutorial/"
    }
}