package com.example.githubtrending.utils.bindingadapters

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubtrending.R

@BindingAdapter(
    value = ["imageUrl", "isCircle"],
    requireAll = false
)
fun ImageView.setImageUrl(
    imageUrl: String?,
    isCircle: Boolean = false
) {
    val placeholder = ContextCompat.getDrawable(context, R.drawable.ic_placeholder)

    Glide.with(this)
        .load(imageUrl)
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        .apply {
            if (isCircle) apply(RequestOptions.circleCropTransform())
        }
        .into(this)
}