package com.futured.app.data.database

import com.futured.app.Coin
import com.futured.app.createDriver
import com.futured.app.db.CommonDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class DatabaseManager {
    private val db: CommonDatabase = CommonDatabase(createDriver())

    fun getAll(): Flow<List<Coin>> = db.coinEntityQueries
        .selectAll()
        .asFlow()
        .mapToList()

    suspend fun insertAll(list: List<Coin>) {
        list.forEach {
            db.coinEntityQueries.insertCoin(it.id, it.name, it.icon, it.symbol, it.price)
        }
    }

    suspend fun deleteAll() = db.coinEntityQueries.deleteAll()
}
