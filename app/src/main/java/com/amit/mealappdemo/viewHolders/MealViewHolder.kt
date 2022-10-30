package com.amit.mealappdemo.viewHolders

import com.amit.mealappdemo.callback.ItemActionCallBack
import com.amit.mealappdemo.databinding.ItemMealBinding
import com.amit.mealappdemo.model.Meal
import com.squareup.picasso.Picasso

class MealViewHolder(val binding: ItemMealBinding) : BaseViewHolder(binding.root) {

    private var itemActionCallBack: ItemActionCallBack? = null

    override fun bind(item: Any) {
        item?.let{ itemAny->
            val meal:Meal =(itemAny as Meal)
            binding.mealName.text = meal.strMeal
                meal.strMealThumb?.let {
                    Picasso.get().load(it)
//            .placeholder(R.drawable.ic_loading)
//                .resize(100, 160)
                        .into(binding.banner)
                }

            binding.root.setOnClickListener {
                itemActionCallBack?.let{callback->
                    callback.onItemClick(adapterPosition,meal)
                }
            }
        }

    }

    fun setItemActionCallBack(itemActionCallBack: ItemActionCallBack?){
        this.itemActionCallBack=itemActionCallBack
    }

}