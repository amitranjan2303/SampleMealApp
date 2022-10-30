package com.amit.mealappdemo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amit.mealappdemo.adapters.MealAdapter
import com.amit.mealappdemo.databinding.FragmentMealDetailsBinding
import com.amit.mealappdemo.model.MealDetailItem
import com.amit.mealappdemo.viewmodels.MealsViewModel
import java.util.*

class MealDetailsFragment : Fragment() {

    private var binding: FragmentMealDetailsBinding? = null
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
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            arguments?.getString("mid")?.let { mealId ->
                viewModel?.getMealDetails(mealId)
            }
        }
        observeChanges()
    }

    fun observeChanges() {
        viewModel?.getMealDetailsLiveData()?.observe(viewLifecycleOwner, Observer {meals ->
            val list = ArrayList<Any>()
            list.addAll(meals)
            setUpRecyclerView(list)
        })
    }
    fun setUpRecyclerView(list: ArrayList<Any>) {
        binding?.rv?.layoutManager = LinearLayoutManager(context)
        mAdapter = MealAdapter()
//        mAdapter?.setItemCallBack(itemCallBack)
        mAdapter?.updateList(list)
        binding?.rv?.adapter = mAdapter
    }
}