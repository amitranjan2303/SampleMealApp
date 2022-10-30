package com.amit.mealappdemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amit.mealappdemo.R
import com.amit.mealappdemo.callback.ItemActionCallBack
import com.amit.mealappdemo.databinding.ItemMealBinding
import com.amit.mealappdemo.databinding.ItemMealsDetailsBinding
import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetailItem
import com.amit.mealappdemo.viewHolders.BaseViewHolder
import com.amit.mealappdemo.viewHolders.MealDetailsViewHolder
import com.amit.mealappdemo.viewHolders.MealViewHolder
import java.util.*

class MealAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var mealList: ArrayList<Any>? = null
    private var itemCallback: ItemActionCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var viewHolder: BaseViewHolder?=null
        if (viewType == R.layout.item_meal) {
            var viewBinding: ItemMealBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
                )
            viewHolder = MealViewHolder(viewBinding)
        } else if (viewType == R.layout.item_meals_details) {
            var viewBinding: ItemMealsDetailsBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
                )
            viewHolder = MealDetailsViewHolder(viewBinding)
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is MealViewHolder) {
            mealList?.get(position).let { (holder as MealViewHolder).bind(it as Meal)
                (holder as MealViewHolder).setItemActionCallBack(itemCallback)
            }
        } else {
            mealList?.get(position)
                .let { (holder as MealDetailsViewHolder).bind(it as MealDetailItem) }
        }
    }

    override fun getItemCount(): Int {
        var count: Int = 0
        count = mealList?.size!!
        return count
    }

    override fun getItemViewType(position: Int): Int {
        var item: Any? = mealList?.get(position)
        var viewType = -1;
        if (item is Meal) {
            viewType = R.layout.item_meal
        } else if (item is MealDetailItem) {
            viewType = R.layout.item_meals_details
        }
        return viewType
    }

    fun updateList(list: ArrayList<Any>) {
        mealList = list
        notifyDataSetChanged()
    }

    fun setItemCallBack(itemCallback: ItemActionCallBack?) {
        this.itemCallback = itemCallback
    }
}