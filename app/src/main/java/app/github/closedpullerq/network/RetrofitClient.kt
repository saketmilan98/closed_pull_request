package app.github.closedpullerq.network

import app.github.closedpullerq.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import app.github.closedpullerq.app.MyApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getInstance(): Retrofit {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor{ it.proceed(it.request().newBuilder().addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_AUTH_TOKEN}").build())}
            .build()

        return Retrofit.Builder()
            .baseUrl(MyApp.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
    }
}