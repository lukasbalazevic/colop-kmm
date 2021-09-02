package com.futured.app.data.store

import com.futured.app.Coin
import com.futured.app.data.Endpoint
import com.futured.app.data.api.RestApiManager
import com.futured.app.data.database.DatabaseManager
import com.futured.app.data.model.response.CoinResponse
import com.futured.app.data.model.response.CoinsResponse
import kotlinx.coroutines.flow.Flow

internal class CoinStore internal constructor(
    private val api: RestApiManager,
    private val db: DatabaseManager
) {
    suspend fun fetchCoins(): CoinsResponse = api.executeRequest(Endpoint.GetCoins())

    suspend fun storeCoins(coins: List<CoinResponse>) {

        return db.insertAll(coins.map {
            Coin(
                it.id, it.name, it.icon, it.symbol, it.price
            )
        })
    }

    fun observeCoins(): Flow<List<Coin>> = db.getAll()
}
