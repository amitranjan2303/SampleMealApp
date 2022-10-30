package com.amit.mealappdemo.utils

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.amit.mealappdemo.R
import com.amit.mealappdemo.fragments.MealFragment

internal fun MealFragment.navigateToMealDetails(args: Bundle) {
    if (findNavController().currentDestination?.id == R.id.mealFragment) {
        findNavController().navigate(
            R.id.action_meal_list_to_meal_details,
            args)
    }
}



