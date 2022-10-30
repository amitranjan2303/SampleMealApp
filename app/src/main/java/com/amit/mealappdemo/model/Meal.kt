package com.amit.mealappdemo.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "Meal")
data class Meal(
//    var type:String?="meal",
    @SerializedName("strMeal")
    @ColumnInfo(name = "strMeal")
    var strMeal: String? = null,

    @SerializedName("strMealThumb")
    @ColumnInfo(name = "strMealThumb")
    var strMealThumb: String? = null,

    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    @ColumnInfo(name = "idMeal")
    var idMeal: String
)