package com.amit.mealappdemo.viewmodels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amit.mealappdemo.MealDemoApplication
import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetailItem
import com.amit.mealappdemo.model.MealDetails
import com.amit.mealappdemo.model.Meals
import com.amit.mealappdemo.repo.IMealRepo
import com.amit.mealappdemo.repo.MealRepoImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


class MealsViewModel : BaseViewModel() {

    private var allMealLiveData: MutableLiveData<List<Any>>? = null
    private var mealDetailsLiveData: MutableLiveData<List<Any>>? = null
    private lateinit var mealRepository: IMealRepo

    init {
        mealRepository = MealRepoImpl(this)
        allMealLiveData = MutableLiveData<List<Any>>()
        mealDetailsLiveData = MutableLiveData<List<Any>>()
    }

    fun getAllMealLiveData(): LiveData<List<Any>>? {
        return allMealLiveData
    }

    fun getMealDetailsLiveData(): LiveData<List<Any>>? {
        return mealDetailsLiveData
    }

    fun getAllMeal() {
        viewModelScope.launch {
            mealRepository?.getAllMeals("dessert")
        }
    }

    fun getMealDetails(mealId: String?) {
        viewModelScope.launch {
            mealRepository?.getMealsDetails(mealId)
        }
    }

    override fun onResponse(isRemoteResponse: Boolean?, any: Any?) {
        if (any is Meals) {
//            (any as Meals)?.allMeals.let { Log.d("Amit", it?.toString()) }
//            Log.d("Amit--","onResponse"+(any as Meals)?.allMeals.toString())
//            Log.d("Amit--","onResponse-"+((any as Meals)?.allMeals?.get(2) is Meal))
//            Log.d("Amit--","onResponse-"+((any as Meals)?.allMeals?.get(2) ))
            (any as Meals)?.allMeals.also { allMealLiveData?.value = it }
            if (isRemoteResponse!!) {
                saveMealListToDB((any as Meals)?.allMeals as List<Meal>)
            }
        } else if (any is MealDetails) {
            (any as MealDetails)?.mealDetails.also { mealDetailsLiveData?.value = it }
            if (isRemoteResponse!!) {
                saveMealDetailsToDB((any as MealDetails)?.mealDetails as List<MealDetailItem>)
            }
        }
    }

    override fun onFailure(error: Throwable?) {
        Toast.makeText(MealDemoApplication.getAppContext(), "Opps Error Occured", Toast.LENGTH_LONG)
            .show()
    }


    private fun saveMealListToDB(list: List<Meal>) {
        viewModelScope.launch {
            mealRepository?.saveMealListToDb(list)
        }
    }

    private fun saveMealDetailsToDB(list: List<MealDetailItem>) {
        viewModelScope.launch {
            mealRepository?.saveMealDetailsToDb(list)
        }
    }

    fun isMealsDetailsExist(mealId: String?): Boolean {
        var isExist: Boolean = false
        runBlocking {
            async {
                isExist = mealRepository?.isMealsDetailsExist(mealId)
            }
        }
//        viewModelScope.async {
//            return@async
//        }
        return isExist
    }
}