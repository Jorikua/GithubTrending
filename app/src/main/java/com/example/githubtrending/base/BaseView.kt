package com.example.githubtrending.base

import androidx.annotation.LayoutRes
import kotlin.reflect.KClass

interface BaseView<VM : BaseViewModel<out BaseState>> {

    fun viewModelClass(): KClass<VM>

    @LayoutRes
    fun layout(): Int
}
