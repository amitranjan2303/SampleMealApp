package com.amit.mealappdemo.network

import com.amit.mealappdemo.MealDemoApplication
import com.amit.mealappdemo.utils.AppUtility
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.CacheControl
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

import java.util.concurrent.TimeUnit

object AppRetrofitClient {

    val loggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

//    var httpCacheDirectory: File = File(MealDemoApplication.getAppContext()?.getCacheDir(), "offlineCache")

    //100 MB
//    var cache: Cache = Cache(httpCacheDirectory, (100 * 1024 * 1024).toLong())

    var okHttpClient = OkHttpClient.Builder()
//        .cache(cache)
//        .addInterceptor(loggingInterceptor)
//        .addNetworkInterceptor(provideCacheInterceptor())
//        .addInterceptor(provideOfflineCacheInterceptor())
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(AppUtility.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }


    private fun provideOfflineCacheInterceptor(): Interceptor? {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response? {
                return try {
                    chain.proceed(chain.request())
                } catch (e: Exception) {
                    val cacheControl = CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()
                    val offlineRequest: Request = chain.request().newBuilder()
                        .cacheControl(cacheControl)
                        .build()
                    chain.proceed(offlineRequest)
                }
            }
        }
    }


    private fun provideCacheInterceptor(): Interceptor? {
        return Interceptor { chain ->
            var request = chain.request()
            val originalResponse = chain.proceed(request)
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.equals("no-store") || cacheControl.equals(
                    "no-cache"
                ) ||
                cacheControl.equals("must-revalidate") || cacheControl.equals("max-stale=0")
            ) {
                val cc = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                /*return originalResponse.newBuilder()
                                .header("Cache-Control", "public, max-stale=" + 60 * 60 * 24)
                                .build();*/
                request = request.newBuilder()
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)
            } else {
                originalResponse
            }
        }
    }
}