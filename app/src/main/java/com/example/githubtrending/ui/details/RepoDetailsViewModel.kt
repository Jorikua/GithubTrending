package com.example.githubtrending.ui.details

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetRepoDetailsUseCase
import com.example.githubtrending.base.BaseViewModel
import com.example.githubtrending.utils.intFormat
import kotlinx.coroutines.launch

class RepoDetailsViewModel(
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase
): BaseViewModel<RepoDetailsState>(RepoDetailsState()) {

    fun getRepoDetails(author: String, repoName: String) {
        viewModelScope.launch {
            getRepoDetailsUseCase.invoke(GetRepoDetailsUseCase.Params(author, repoName)).handleSuccess { repo ->
                with(repo) {
                    state.value = currentState.copy(
                        avatarUrl = owner.avatarUrl,
                        author = owner.name,
                        name = name,
                        desc =  description,
                        stars = intFormat.format(stars),
                        forks = intFormat.format(forks),
                        issues = intFormat.format(issues),
                        watchers = intFormat.format(watchers),
                        license = license?.name.orEmpty(),
                        currentBranch = branch,
                        isPrivate = isPrivate,
                        fullName = fullName
                    )
                }
            }
        }
    }
}