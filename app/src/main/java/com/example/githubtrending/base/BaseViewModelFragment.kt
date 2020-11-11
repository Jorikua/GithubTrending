package com.example.githubtrending.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.githubtrending.BR
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class BaseViewModelFragment<VM : BaseViewModel<out BaseState>, B : ViewDataBinding>
    : Fragment(), BaseView<VM> {

    open val vm: VM by lazy { getViewModel(viewModelClass()) }

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDataBinding(inflater, container)
        observeExceptions()
        return binding.root
    }

    private fun initDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.vm, vm)
    }

    private fun observeExceptions() {
        vm.exception.observe(viewLifecycleOwner) {
            activity?.let { activity ->
                if (activity.isFinishing) return@observe
                Snackbar.make(
                    binding.root,
                    it.message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

}
