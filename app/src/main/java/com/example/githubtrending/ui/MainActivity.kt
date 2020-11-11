package com.example.githubtrending.ui

import com.example.githubtrending.R
import com.example.githubtrending.base.BaseViewModelActivity
import com.example.githubtrending.databinding.ActivityMainBinding
import kotlin.reflect.KClass

class MainActivity : BaseViewModelActivity<MainViewModel, ActivityMainBinding>() {

    override fun viewModelClass(): KClass<MainViewModel> = MainViewModel::class

    override fun layout(): Int = R.layout.activity_main
}