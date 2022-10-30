package com.amit.mealappdemo.callback

interface NetworkCallBack {
    fun onResponse(isRemoteResponse: Boolean?,any: Any?)
    fun onFailure(error: Throwable?)
}