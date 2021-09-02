package com.futured.app.data

import com.futured.app.data.model.response.CoinsResponse
import com.futured.app.tool.Constants
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer

internal sealed class Endpoint<R>(
    internal val url: String,
    internal val method: HttpMethod,
    internal val responseSerializer: KSerializer<R>? = null
) {
    class GetCoins(currency: String = "EUR") : Endpoint<CoinsResponse>(
        Constants.baseUrl + "public/v1/coins?currency=$currency",
        HttpMethod.Get,
        CoinsResponse.serializer()
    )
}
