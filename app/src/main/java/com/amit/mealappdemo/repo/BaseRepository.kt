package com.amit.mealappdemo.repo

import com.amit.mealappdemo.MealDemoApplication
import com.amit.mealappdemo.appDb.MealAppDB
import com.amit.mealappdemo.network.AppRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.DriverManager.println

abstract class BaseRepository<out S> {

    protected val TAG: String = BaseRepository::class.java.simpleName
    abstract fun onError(error: Throwable)
    abstract fun onSuccess(body: Any)
    protected var appDb: MealAppDB
    protected val service: S

    constructor(clazz: Class<S>) {
        service = AppRetrofitClient.buildService(clazz)
        appDb = MealAppDB.getDataBase(MealDemoApplication.getAppContext()!!)
    }

    protected fun <T> enqueue(call: Call<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                try {
                    val data = response.body()
                    if (response.isSuccessful) {
                        if (data != null) {
                            println("SUCCESS")
                            onSuccess(data)
                        }
                    } else {
                        // error response, no access to resource?
                        println("FAIL: Response Unsuccessful")
                        onError(java.lang.Exception("Opps! something went wrong"))
                    }
                } catch (e: java.lang.Exception) {
                    onError(e)
                }
            }

            override fun onFailure(call: Call<T>, error: Throwable) {
                // handle failure
                println("FAIL: Call Failed")
                println(error.message)
                onError(java.lang.Exception("Opps! something went wrong"))
            }
        })
    }
}
