package com.amit.mealappdemo.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amit.mealappdemo.MealDemoApplication
import com.amit.mealappdemo.R
import com.amit.mealappdemo.adapters.MealAdapter
import com.amit.mealappdemo.callback.ItemActionCallBack
import com.amit.mealappdemo.databinding.FragmentMealBinding
import com.amit.mealappdemo.model.Meal
import com.amit.mealappdemo.model.MealDetails
import com.amit.mealappdemo.model.Meals
import com.amit.mealappdemo.utils.AppUtility
import com.amit.mealappdemo.utils.navigateToMealDetails
import com.amit.mealappdemo.viewmodels.MealsViewModel
import java.util.*

class MealFragment : Fragment() {

    private var binding: FragmentMealBinding? = null
    private var viewModel: MealsViewModel? = null
    private var mAdapter: MealAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MealsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.getAllMeal()
        observeChanges()
    }

    fun observeChanges() {
        viewModel?.getAllMealLiveData()?.observe(viewLifecycleOwner, Observer { meals ->
            val list = ArrayList<Any>()
            list.addAll(meals)
            setUpRecyclerView(list)
        })
    }

    fun setUpRecyclerView(list: ArrayList<Any>) {
        binding?.rv?.layoutManager = LinearLayoutManager(context)
        mAdapter = MealAdapter()
        mAdapter?.setItemCallBack(itemCallBack)
        mAdapter?.updateList(list)
        binding?.rv?.adapter = mAdapter

        binding?.etSearch?.addTextChangedListener(textChangeListener)
    }

    private val itemCallBack: ItemActionCallBack = object : ItemActionCallBack {
        override fun onItemClick(position: Int?, item: Any?) {
            if (item is Meal) {
                if (AppUtility.isNetworkAvailable(MealDemoApplication.getAppContext()!!)) {
                    moveToDetailsFragment(item.idMeal)
                } else {
                    val isExist = viewModel?.isMealsDetailsExist(item.idMeal)
                    if (isExist!!) {
                        moveToDetailsFragment(item.idMeal)
                    } else {
                        Toast.makeText(
                            MealDemoApplication.getAppContext(),
                            "Please Check Internet Connection",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
    }

    fun moveToDetailsFragment(mealId: String?) {
        val bundle = Bundle()
        bundle.putString("mid", mealId)
        navigateToMealDetails(bundle)
    }

    fun filterUserInput(userInput: String) {
        var localList = ArrayList<Any>()
        viewModel?.getAllMealLiveData()?.value?.let { actualMealList ->
            for (mealItem in actualMealList) {
                if (mealItem is Meal) {
                    (mealItem as Meal).strMeal?.let { mealNameString ->
                        if (mealNameString.toLowerCase().startsWith(userInput)) {
                            localList.add(mealItem)
                        }
                    }
                }
            }
        }

        mAdapter?.updateList(localList)
    }

    var textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (!TextUtils.isEmpty(s)) {
                filterUserInput(s.toString().toLowerCase())
            } else {
                val item: ArrayList<Any> = viewModel?.getAllMealLiveData()?.value as ArrayList<Any>
                mAdapter?.updateList(item)
            }
        }
    }
}