package com.example.githubtrending.utils.bindingadapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.example.githubtrending.utils.extensions.dpToPx

@BindingConversion
fun visibleOrGone(expression: Boolean?): Int {
    return if (expression == true) View.VISIBLE else View.GONE
}

@BindingAdapter(
    value = [
        "marginStart",
        "marginTop",
        "marginEnd",
        "marginBottom"
    ],
    requireAll = false
)
fun View.bindMargins(
    start: Int?, top: Int?, end: Int?, bottom: Int?
) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    with(lp) {
        if (start != null) marginStart = context.dpToPx(start)
        if (end != null) lp.marginEnd = context.dpToPx(end)

        if (top != null) topMargin = context.dpToPx(top)
        if (bottom != null) bottomMargin = context.dpToPx(bottom)
    }
    layoutParams = lp
}