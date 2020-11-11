package com.example.domain.mapper

import com.example.data.model.BuiltByResponse
import com.example.domain.model.BuiltBy

fun BuiltByResponse.toBuiltBy(): BuiltBy {
    return BuiltBy(
        username = username,
        href = href,
        avatar = avatar
    )
}