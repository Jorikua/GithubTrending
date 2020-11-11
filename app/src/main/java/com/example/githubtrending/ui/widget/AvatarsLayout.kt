package com.example.githubtrending.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.example.githubtrending.R
import com.example.githubtrending.utils.bindingadapters.setImageUrl
import kotlinx.android.synthetic.main.layout_avatars.view.*

class AvatarsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val images by lazy { listOf<ImageView>(iv1, iv2, iv3, iv4, iv5) }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_avatars, this, true)
    }

    fun updateAvatars(avatars: List<String>) {
        images.forEachIndexed { index, imageView ->
            val url = avatars.getOrNull(index)
            val isUrlEmpty = url.isNullOrEmpty()
            imageView.isGone = isUrlEmpty
            if (isUrlEmpty) return

            imageView.setImageUrl(url, true)
        }
    }
}