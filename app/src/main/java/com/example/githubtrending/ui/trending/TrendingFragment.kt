package com.example.githubtrending.ui.trending

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.OnModelBuildFinishedListener
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.example.githubtrending.R
import com.example.githubtrending.base.BaseViewModelFragment
import com.example.githubtrending.databinding.FragmentTrendingBinding
import com.example.githubtrending.ui.MainViewModel
import com.example.githubtrending.ui.model.Order
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

class TrendingFragment : BaseViewModelFragment<TrendingViewModel, FragmentTrendingBinding>() {
    override fun viewModelClass(): KClass<TrendingViewModel> = TrendingViewModel::class

    override fun layout(): Int = R.layout.fragment_trending

    private val controller by inject<TrendingController> { parametersOf(controllerCallback) }

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()

        mainViewModel.filter.observe(viewLifecycleOwner) {
            vm.getTrendingRepos(it)
        }

        with(binding.rv) {
            setController(controller)
            layoutManager = StickyHeaderLinearLayoutManager(requireContext())
        }

        vm.state.observe(viewLifecycleOwner) { state ->
            with(controller) {
                setData(state)
                removeModelBuildListener(buildListener)
                addModelBuildListener(buildListener)
            }
        }

        vm.event.observe(viewLifecycleOwner) {
            when (it) {
                is TrendingViewModel.Event.ToSpokenLanguageFilter -> findNavController().navigate(
                    TrendingFragmentDirections.toSpokenFragment()
                )
                is TrendingViewModel.Event.ToGranularityFilter -> findNavController().navigate(
                    TrendingFragmentDirections.toGranularityFragment()
                )
                is TrendingViewModel.Event.ToProgrammingLanguageFilter -> findNavController().navigate(
                    TrendingFragmentDirections.toProgrammingFragment()
                )
            }
        }
    }

    private val buildListener = OnModelBuildFinishedListener {
        val state = vm.state.value ?: return@OnModelBuildFinishedListener
        if (state.scrollToTop) {
            binding.rv.scrollToPosition(0)
        }
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            inflateMenu(R.menu.trending)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.sort -> {
                        mainViewModel.filter.value?.order?.let { order ->
                            showSortMenu(order.itemId)
                        }
                        true
                    }
                    R.id.reset -> {
                        mainViewModel.reset()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private val controllerCallback = object : TrendingController.Callback {
        override fun onItemClick(author: String, repoName: String) {
            findNavController().navigate(
                TrendingFragmentDirections.toRepoDetailsFragment(
                    author,
                    repoName
                )
            )
        }
    }

    private fun showSortMenu(orderItemId: Int) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(getString(R.string.ordering))
            val orders = Order.values()
            val checkedItem = orders.indexOfFirst { it.itemId == orderItemId }
            setSingleChoiceItems(
                orders.map { getString(it.titleResId) }.toTypedArray(),
                checkedItem
            ) { dialog, _ ->
                val position = (dialog as AlertDialog).listView.checkedItemPosition
                mainViewModel.order(orders[position].itemId)
                dialog.dismiss()
            }
            create().show()
        }
    }
}