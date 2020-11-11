package com.example.base.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourceManagerImpl(private val context: Context) : ResourceManager {
    override fun getString(@StringRes res: Int): String {
        return context.getString(res)
    }

    override fun getString(@StringRes res: Int, vararg arguments: Any?): String {
        return context.getString(res, *arguments)
    }

    override fun getColor(res: Int): Int {
        return context.getColor(res)
    }

    override fun getDrawable(res: Int): Drawable {
        return ContextCompat.getDrawable(context, res)!!
    }

    override fun getPlural(res: Int, quantity: Int, vararg format: Any?): String {
        return context.resources.getQuantityString(res, quantity, *format)
    }

    override fun getPlural(res: Int, quantity: Int): String {
        return context.resources.getQuantityString(res, quantity, quantity)
    }
}