package com.amit.mealappdemo.appDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetailItem

@Dao
interface MealDao {

    @Query("select * from Meal")
    fun getAllMeal(): List<Meal>

    @Query("select * from MealDetailItem")
    fun getAllMealDetails(): List<MealDetailItem>

    @Query("SELECT * FROM MealDetailItem WHERE idMeal =:mealId")
//    @Query("select * from MealDetailItem")
    fun getMealDetails(mealId: String?): MealDetailItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMealList(list: List<Meal>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMealDetails(mealDetailItem: MealDetailItem?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMealDetailsList(list: List<MealDetailItem>?)
}