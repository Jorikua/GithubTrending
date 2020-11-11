package com.example.githubtrending.utils

data class Selectable<T: Any>(
    val item: T,
    val isSelected: Boolean
)