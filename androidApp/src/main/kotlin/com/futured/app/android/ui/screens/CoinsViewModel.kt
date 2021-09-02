package com.futured.app.android.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.futured.arkitekt.kmusecases.scope.CoroutineScopeOwner
import com.futured.app.Coin
import com.futured.app.domain.GetCoinsListUseCase
import kotlinx.coroutines.CoroutineScope

class CoinsViewModel : ViewModel(), CoroutineScopeOwner {
    private val getCoinsListUseCase = GetCoinsListUseCase()
    var message by mutableStateOf("")

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    fun fetchCoins() {
        getCoinsListUseCase.execute(Unit) {
            onSuccess { message = it }
            onError { message = it.message ?: it.toString() }
        }
    }
}
