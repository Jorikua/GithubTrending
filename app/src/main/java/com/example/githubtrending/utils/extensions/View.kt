package com.example.githubtrending.utils.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun View.hideKeyboard() {
    val manager = context.getSystemService<InputMethodManager>() ?: return
    manager.hideSoftInputFromWindow(windowToken, 0)
}