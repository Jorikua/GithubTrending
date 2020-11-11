package com.example.githubtrending.ui.filter.granularity

import android.os.Bundle
import android.view.View
import com.example.base.resource.ResourceManager
import com.example.githubtrending.R
import com.example.githubtrending.base.BaseViewModelFragment
import com.example.githubtrending.databinding.FragmentGranularityBinding
import com.example.githubtrending.divider
import com.example.githubtrending.filter
import com.example.githubtrending.ui.MainViewModel
import com.example.githubtrending.utils.extensions.getHumanString
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

class GranularityFragment :
    BaseViewModelFragment<GranularityViewModel, FragmentGranularityBinding>() {
    override fun viewModelClass(): KClass<GranularityViewModel> = GranularityViewModel::class

    override fun layout(): Int = R.layout.fragment_granularity

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val resourceManager by inject<ResourceManager>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.filter.observe(viewLifecycleOwner) {
            vm.getGranularities(it.params.granularity)
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        vm.state.observe(viewLifecycleOwner) { state ->
            binding.rv.withModels {
                state.granularities.forEach {
                    val item = it.item
                    filter {
                        id(item.ordinal)
                        title(item.getHumanString(resourceManager))
                        isSelected(it.isSelected)
                        onClick { _ ->
                            mainViewModel.onGranularityLanguageSelected(item)
                            requireActivity().onBackPressed()
                        }
                    }

                    divider {
                        id("${item.name}${item.ordinal}divider")
                        marginsHorizontal(16)
                    }
                }
            }
        }
    }
}