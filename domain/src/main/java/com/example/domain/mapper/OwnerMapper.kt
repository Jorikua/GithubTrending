package com.example.domain.mapper

import com.example.data.model.OwnerResponse
import com.example.domain.model.Owner

fun OwnerResponse.toOwner(): Owner {
    return Owner(
        id = id,
        name = name,
        avatarUrl = avatar
    )
}