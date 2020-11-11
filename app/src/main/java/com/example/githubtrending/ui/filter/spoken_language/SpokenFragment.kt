package com.example.githubtrending.ui.filter.spoken_language

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyControllerAdapter
import com.airbnb.epoxy.IdUtils
import com.airbnb.epoxy.OnModelBuildFinishedListener
import com.example.githubtrending.R
import com.example.githubtrending.base.BaseViewModelFragment
import com.example.githubtrending.databinding.FragmentSpokenLanguagesBinding
import com.example.githubtrending.divider
import com.example.githubtrending.filter
import com.example.githubtrending.ui.MainViewModel
import com.example.githubtrending.utils.extensions.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

class SpokenFragment : BaseViewModelFragment<SpokenViewModel, FragmentSpokenLanguagesBinding>() {
    override fun viewModelClass(): KClass<SpokenViewModel> = SpokenViewModel::class

    override fun layout(): Int = R.layout.fragment_spoken_languages

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.filter.observe(viewLifecycleOwner) {
            vm.getLanguages(it.params.spokenLanguageCode)
        }

        binding.toolbar.setNavigationOnClickListener {
            goBack(it)
        }

        binding.rv.setOnTouchListener { v, _ ->
            v.hideKeyboard()
            false
        }

        vm.state.observe(viewLifecycleOwner) { state ->
            binding.rv.withModels {
                state.filteredLanguages.forEach {
                    val item = it.item
                    filter {
                        id(item.id)
                        title(item.name)
                        isSelected(it.isSelected)
                        onClick { view ->
                            mainViewModel.onSpokenLanguageSelected(item.name, item.code)
                            goBack(view)
                        }
                    }

                    divider {
                        id("${item.name}${item.code}divider")
                        marginsHorizontal(16)
                    }
                }

                removeModelBuildListener(buildListener)
                addModelBuildListener(buildListener)
            }
        }
    }

    private fun goBack(view: View) {
        view.hideKeyboard()
        requireActivity().onBackPressed()
    }

    private val buildListener = OnModelBuildFinishedListener {
        val state = vm.state.value ?: return@OnModelBuildFinishedListener
        val adapter = binding.rv.adapter as? EpoxyControllerAdapter ?: return@OnModelBuildFinishedListener
        val item = state.filteredLanguages.firstOrNull { it.isSelected }?.item ?: return@OnModelBuildFinishedListener
        val model = adapter.getModelById(IdUtils.hashString64Bit(item.id)) ?: return@OnModelBuildFinishedListener
        val position = adapter.getModelPosition(model)
        binding.rv.scrollToPosition(position)
    }
}