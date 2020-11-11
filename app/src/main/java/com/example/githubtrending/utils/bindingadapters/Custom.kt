package com.example.githubtrending.utils.bindingadapters

import androidx.databinding.BindingAdapter
import com.example.githubtrending.ui.widget.AvatarsLayout

@BindingAdapter("setAvatars")
fun AvatarsLayout.setAvatars(avatars: List<String>) {
    updateAvatars(avatars)
}