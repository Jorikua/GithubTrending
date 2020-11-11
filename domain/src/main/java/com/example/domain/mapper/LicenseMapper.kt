package com.example.domain.mapper

import com.example.data.model.LicenseResponse
import com.example.domain.model.License

fun LicenseResponse.toLicense(): License {
    return License(
        name = name,
        key = key,
        url = url
    )
}