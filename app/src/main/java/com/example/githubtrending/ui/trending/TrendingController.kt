package com.example.githubtrending.ui.trending

import com.airbnb.epoxy.TypedEpoxyController
import com.example.githubtrending.divider
import com.example.githubtrending.listRepo

class TrendingController(
    private val callback: Callback
): TypedEpoxyController<TrendingState>() {
    override fun buildModels(data: TrendingState) {
        data.trendings.forEach {
            listRepo {
                id("${it.author}${it.name}")
                model(it)
                onClick { _ -> callback.onItemClick(it.author, it.name) }
            }

            divider {
                id("${it.author}${it.name}divider")
                marginsHorizontal(16)
            }
        }
    }

    interface Callback {
        fun onItemClick(author: String, repoName: String)
    }
}