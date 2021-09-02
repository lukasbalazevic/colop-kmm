package com.futured.app.domain

import app.futured.arkitekt.kmusecases.freeze
import app.futured.arkitekt.kmusecases.usecase.UseCase
import com.futured.app.data.api.RestApiManager

class GetCoinsListUseCase : UseCase<Unit, String>() {
    private val rs = RestApiManager()
    init {
        freeze()
    }

    override suspend fun build(arg: Unit): String {
        return rs.NoLimit()
    }
}
