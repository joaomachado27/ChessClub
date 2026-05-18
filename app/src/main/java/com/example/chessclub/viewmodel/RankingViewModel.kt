package com.example.chessclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessclub.data.UserRepository
import com.example.chessclub.models.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RankingViewModel(private val repository: UserRepository) : ViewModel() {

    val ranking: StateFlow<List<User>> = repository.getRanking()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
