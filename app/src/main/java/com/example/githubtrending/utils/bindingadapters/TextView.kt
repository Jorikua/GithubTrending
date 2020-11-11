package com.example.githubtrending.utils.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("drawablesTintColor")
fun TextView.setDrawablesTintColor(color: Int) {
    compoundDrawablesRelative.forEach {
        it?.setTint(color)
    }
}