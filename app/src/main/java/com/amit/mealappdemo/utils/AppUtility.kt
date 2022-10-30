package com.amit.mealappdemo.utils

import android.content.Context
import android.net.ConnectivityManager

class AppUtility {

    companion object {
        val BASE_URL = "https://www.themealdb.com"
        const val BASE_PATH = "/api/json/v1/1/"
        const val ALL_MEALS = BASE_PATH+"filter.php"
        const val MEAL_DETAILS = BASE_PATH+"lookup.php"

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}