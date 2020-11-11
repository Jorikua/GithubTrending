package com.example.domain.model

data class Language(
    val name: String,
    val code: String?
) {

    val id: String get() = "$name$code"
}