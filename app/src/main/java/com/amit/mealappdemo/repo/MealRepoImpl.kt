package com.amit.mealappdemo.repo

import android.util.Log
import com.amit.mealappdemo.MealDemoApplication
import com.amit.mealappdemo.callback.NetworkCallBack
import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetailItem
import com.amit.mealappdemo.model.MealDetails
import com.amit.mealappdemo.model.Meals
import com.amit.mealappdemo.network.ApiService
import com.amit.mealappdemo.utils.AppUtility
import retrofit2.Call
import java.util.*

class MealRepoImpl(val networkCallBack: NetworkCallBack) :
    BaseRepository<ApiService>(ApiService::class.java), IMealRepo {

    override suspend fun getAllMeals(query: String?) {
        Log.d("Amit--","Network -------- "+AppUtility.isNetworkAvailable(MealDemoApplication.getAppContext()!!))
        if (AppUtility.isNetworkAvailable(MealDemoApplication.getAppContext()!!)) {
            var call: Call<Meals> = service.getAllMeals(query)
            enqueue(call)
        } else {
            val list: List<Meal> = appDb.getMealDao().getAllMeal()
            val meals = Meals()
            meals.allMeals?.addAll(list)
            networkCallBack?.onResponse(false,list)
        }
    }

    override suspend fun getMealsDetails(mealId: String?) {
        if (AppUtility.isNetworkAvailable(MealDemoApplication.getAppContext()!!)) {
            var call: Call<MealDetails> = service.getMealsDetails(mealId)
            enqueue(call)
        } else {
            val mealDetials: MealDetailItem = appDb.getMealDao().getMealDetails(mealId)
            val meals = MealDetails()
            meals.mealDetails?.add(mealDetials)
            networkCallBack?.onResponse(false,meals)
        }
    }

    override suspend fun isMealsDetailsExist(mealId: String?): Boolean {
        var isExist: Boolean = false
        var mealDetailItem = appDb.getMealDao().getMealDetails(mealId)
        if (mealDetailItem != null) {
            isExist = true
        }
        return isExist
    }

    override suspend fun saveMealListToDb(mealList: List<Meal>?) {
        appDb.getMealDao().saveMealList(mealList)
    }

    override suspend fun saveMealDetailsToDb(mealDetails: MealDetailItem?) {
        appDb.getMealDao().saveMealDetails(mealDetails)
    }

    override suspend fun saveMealDetailsToDb(mealDetails: List<MealDetailItem>?) {
        appDb.getMealDao().saveMealDetailsList(mealDetails)
    }

    override fun onSuccess(body: Any) {
        networkCallBack?.onResponse(true,body)
    }

    override fun onError(error: Throwable) {
        networkCallBack.onFailure(error)
    }
}