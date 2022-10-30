package com.amit.mealappdemo.repo

import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetailItem

open interface IMealRepo {

    suspend fun getAllMeals(query: String?)
    suspend fun getMealsDetails(mealId: String?)
    suspend fun isMealsDetailsExist(mealId: String?):Boolean
    suspend fun saveMealListToDb(mealList: List<Meal>?)
    suspend fun saveMealDetailsToDb(mealDetails: MealDetailItem?)
    suspend fun saveMealDetailsToDb(mealDetails: List<MealDetailItem>?)
}