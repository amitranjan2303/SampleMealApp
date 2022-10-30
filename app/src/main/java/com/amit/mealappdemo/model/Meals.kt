package com.amit.mealappdemo.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Meals(
    @SerializedName("meals")
    var allMeals: ArrayList<Meal>? = ArrayList<Meal>()
)