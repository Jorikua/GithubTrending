package com.example.domain.mapper

import com.example.data.model.LanguageResponse
import com.example.domain.model.Language

fun LanguageResponse.toLanguage(): Language {
    return Language(
        name = name,
        code = code
    )
}