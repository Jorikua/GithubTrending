package com.example.githubtrending.utils.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Dimension

fun Context.dpToPx(@Dimension(unit = Dimension.DP) dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()
}