package com.amit.mealappdemo.network

import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetails
import com.amit.mealappdemo.model.Meals
import com.amit.mealappdemo.utils.AppUtility
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(AppUtility.ALL_MEALS)
    fun getAllMeals(
        @Query("c") meals: String?,
    ): Call<Meals>

    @GET(AppUtility.MEAL_DETAILS)
    fun getMealsDetails(
        @Query("i") mealId: String?,
    ): Call<MealDetails>
}