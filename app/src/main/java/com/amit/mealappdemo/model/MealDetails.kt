package com.amit.mealappdemo.model

import com.google.gson.annotations.SerializedName
import java.util.*

class MealDetails(
    @SerializedName("meals")
    var mealDetails: ArrayList<MealDetailItem>? = ArrayList<MealDetailItem>()
)