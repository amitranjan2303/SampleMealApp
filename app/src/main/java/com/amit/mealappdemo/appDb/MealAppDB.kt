package com.amit.mealappdemo.appDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetailItem

@Database(entities = [Meal::class, MealDetailItem::class], version = 1, exportSchema = false)
abstract class MealAppDB : RoomDatabase() {
    companion object {
        private var INSTANCE: MealAppDB? = null
        fun getDataBase(context: Context): MealAppDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MealAppDB::class.java,
                    "meal-app-db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE as MealAppDB
        }
    }

    abstract fun getMealDao(): MealDao
}