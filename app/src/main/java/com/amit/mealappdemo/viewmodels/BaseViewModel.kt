package com.amit.mealappdemo.viewmodels

import android.util.Pair
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amit.mealappdemo.callback.NetworkCallBack

abstract class BaseViewModel : ViewModel(), NetworkCallBack {
    protected var TAG: String = this.javaClass.simpleName
    private var isProgress: MutableLiveData<Boolean>? = MutableLiveData(false)
    private var showSnackBarMutableLiveData: MutableLiveData<Pair<Boolean, Pair<Boolean, String>>>? =
        MutableLiveData<Pair<Boolean, Pair<Boolean, String>>>()

    protected fun showProgress() {
        isProgress?.value = true
    }

    protected fun hideProgress() {
        isProgress?.value = false
    }

    protected fun showSnackBar(isSuccess: Boolean?, message: String?) {
        val result = Pair<Boolean, String>(isSuccess!!, message!!)
        val pair = Pair<Boolean, Pair<Boolean, String>>(true, result!!)
        showSnackBarMutableLiveData?.value = pair
    }

    fun getProgress(): MutableLiveData<Boolean>? {
        return isProgress
    }

    fun getSnackBar(): MutableLiveData<Pair<Boolean, Pair<Boolean, String>>>? {
        return showSnackBarMutableLiveData
    }

}