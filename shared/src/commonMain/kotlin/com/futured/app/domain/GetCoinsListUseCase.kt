package com.futured.app.domain

import app.futured.arkitekt.kmusecases.freeze
import app.futured.arkitekt.kmusecases.usecase.UseCase
import com.futured.app.Coin
import com.futured.app.data.api.RestApiManager
import com.futured.app.data.database.DatabaseManager
import com.futured.app.data.model.ListWrapper
import com.futured.app.data.store.CoinStore

class GetCoinsListUseCase : UseCase<Unit, String>() {
    private val rs = RestApiManager()
    init {
        freeze()
    }

    override suspend fun build(arg: Unit): String {
        return rs.NoLimit()
    }
}
