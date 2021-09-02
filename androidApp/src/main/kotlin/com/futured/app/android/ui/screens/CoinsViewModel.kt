package com.futured.app.android.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.futured.arkitekt.kmusecases.scope.CoroutineScopeOwner
import com.futured.app.domain.GetCoinsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoinsViewModel : ViewModel(), CoroutineScopeOwner {
    private val getCoinsListUseCase = GetCoinsListUseCase()
    var message by mutableStateOf("")

    val color = mutableStateOf(Color.Red)
    val size = mutableStateOf(120.sp)

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    init {
        changeColor()
    }

    fun changeColor() = coroutineScope.launch {
        while (true) {
            color.value = Color.Red
            size.value = 120.sp
            delay(100)
            color.value = Color.Black
            size.value = 125.sp
            delay(100)
            color.value = Color.Blue
            size.value = 130.sp
            delay(100)
            color.value = Color.Cyan
            size.value = 125.sp
            delay(100)
            color.value = Color.DarkGray
            size.value = 123.sp
            delay(100)
            color.value = Color.Magenta
            size.value = 115.sp
            delay(100)
        }
    }

    fun fetchCoins() {
        getCoinsListUseCase.execute(Unit) {
            onSuccess { message = it }
            onError { message = it.message ?: it.toString() }
        }
    }
}
