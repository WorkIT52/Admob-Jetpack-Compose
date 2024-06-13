package com.app.jetpackads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

object LibraryUtils {
    fun Context.getActivity(): Activity? = when (this) {
        is AppCompatActivity -> this
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}