package com.example.githubtrending.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class BaseViewModelActivity<VM : BaseViewModel<out BaseState>, B : ViewDataBinding> : AppCompatActivity(),
    BaseView<VM> {

    protected lateinit var binding: B

    val viewModel: VM by lazy { getViewModel(viewModelClass()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeException()
    }

    private fun initDataBinding() {
        if (layout() == -1) return

        binding = DataBindingUtil.setContentView(this, layout())
        binding.lifecycleOwner = this
    }

    private fun observeException() {
        viewModel.exception.observe(this) {
            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
        }
    }
}
