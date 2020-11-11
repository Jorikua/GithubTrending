package com.example.githubtrending.ui.details

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.example.githubtrending.R
import com.example.githubtrending.base.BaseViewModelFragment
import com.example.githubtrending.databinding.FragmentRepoDetailsBinding
import com.example.githubtrending.detailsHeader
import com.example.githubtrending.divider
import com.example.githubtrending.section
import kotlin.reflect.KClass

class RepoDetailsFragment :
    BaseViewModelFragment<RepoDetailsViewModel, FragmentRepoDetailsBinding>() {
    override fun viewModelClass(): KClass<RepoDetailsViewModel> = RepoDetailsViewModel::class

    override fun layout(): Int = R.layout.fragment_repo_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loadDetails()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.refresh.setOnRefreshListener {
            loadDetails()
        }

        vm.state.observe(viewLifecycleOwner) { state ->

            binding.rv.withModels {
                with(state) {
                    if (state.author.isEmpty()) return@withModels

                    detailsHeader {
                        id("header")
                        avatarUrl(avatarUrl)
                        author(author)
                        name(name)
                        desc(desc)
                        stars(stars)
                        forks(forks)
                        isPrivate(isPrivate)
                    }

                    divider {
                        id("header divider")
                    }

                    addSection(getString(R.string.issues), issues, R.drawable.ic_issue)
                    addSection(getString(R.string.watchers), watchers, R.drawable.ic_eye)

                    if (license.isNotEmpty()) {
                        addSection(getString(R.string.license), license, R.drawable.ic_license)
                    }

                    addSection(getString(R.string.branch), currentBranch, R.drawable.ic_fork)
                }
            }
        }
    }

    private fun loadDetails() {
        val authorName = RepoDetailsFragmentArgs.fromBundle(requireArguments()).author
        val repoName = RepoDetailsFragmentArgs.fromBundle(requireArguments()).repoName
        vm.getRepoDetails(authorName, repoName)
    }

    private fun EpoxyController.addSection(
        title: String,
        value: String,
        @DrawableRes drawableRes: Int
    ) {
        section {
            id(title)
            title(title)
            value(value)
            drawable(ContextCompat.getDrawable(requireContext(), drawableRes))
        }

        divider {
            id("$title divider")
            marginsHorizontal(16)
        }
    }
}