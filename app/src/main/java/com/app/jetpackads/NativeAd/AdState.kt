package com.app.jetpackads

data class AdState(
    val isSuccess :Boolean =  false,
    val isError:Boolean = false,
    val errorMessage :String? = null
)
