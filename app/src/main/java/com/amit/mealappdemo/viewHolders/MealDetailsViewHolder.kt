package com.amit.mealappdemo.viewHolders

import com.amit.mealappdemo.databinding.ItemMealsDetailsBinding
import com.amit.mealappdemo.model.MealDetailItem
import com.squareup.picasso.Picasso

class MealDetailsViewHolder(val binding: ItemMealsDetailsBinding) : BaseViewHolder(binding.root) {
    override fun bind(item: Any) {
        item?.let{ itemAny->
            val meal: MealDetailItem =(itemAny as MealDetailItem)
            binding.title.text = meal.strMeal
            binding.mealDetails.text = meal.strInstructions
            meal.strMealThumb?.let {
                Picasso.get().load(it)
                    .into(binding.banner)
            }

        }
    }
}