package com.example.base.resource

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceManager {
    fun getString(@StringRes res: Int): String
    fun getString(@StringRes res: Int, vararg arguments: Any?): String
    fun getColor(@ColorRes res: Int): Int
    fun getDrawable(@DrawableRes res: Int): Drawable
    fun getPlural(@PluralsRes res: Int, quantity: Int, vararg format: Any?): String
    fun getPlural(@PluralsRes res: Int, quantity: Int): String
}