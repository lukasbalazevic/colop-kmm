package com.futured.app.data.api

import com.futured.app.createActorSelectorManager
import com.futured.app.data.Endpoint
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.network.NetworkAddress
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

internal class RestApiManager {
    private val httpClient = HttpClient()

    private val json = Json(Json) {
        isLenient = true
        ignoreUnknownKeys = true
    }

    suspend fun <R : Any> executeRequest(ep: Endpoint<R>) =
        execute(
            url = Url(ep.url),
            method = ep.method,
            responseSerializer = ep.responseSerializer
        )

    private suspend fun <R : Any> execute(
        url: Url,
        headers: Map<String, String> = mapOf(),
        method: HttpMethod,
//        body: B?,
        responseSerializer: KSerializer<R>? = null,
//        bodySerializer: KSerializer<B>? = null
    ): R {
        val response: HttpResponse = runCatching {

            httpClient.request<HttpResponse> {
                url(url)
                header("Accept", "application/json")
                headers.forEach {
                    header(it.key, it.value)
                }
                this.method = method
//                if (body != null) {
//                    this.body =
//                        json.encodeToString(
//                            serializer = bodySerializer ?: error("Missing body serializer"),
//                            value = body
//                        )
//                }
            }
        }.getOrThrow()

        return if (response.isSuccessful()) {
            val bodyContent = response.readText()
            if (responseSerializer == null) {
                @Suppress("UNCHECKED_CAST")
                Unit as R
            } else {
                json.decodeFromString(responseSerializer, bodyContent)
            }
        } else {
            error("$url ${response.status.value} ")
        }
    }

    private fun HttpResponse.isSuccessful(): Boolean = status.value in 200..299


    suspend fun NoLimitServer(): String {
        val server =
            aSocket(createActorSelectorManager()).tcp().bind(NetworkAddress("127.0.0.1", 5555))
        println("Started echo telnet server at ${server.localAddress}")

        while (true) {
            val socket = server.accept()
            GlobalScope.launch {
                println("Socket accepted: ${socket.remoteAddress}")
                val input = socket.openReadChannel()
                val output = socket.openWriteChannel(autoFlush = true)

                try {
                    while (true) {
                        val line = input.readUTF8Line(Int.MAX_VALUE)

                        println("${socket.remoteAddress}: $line")
                        output.writeStringUtf8("$line\r\n")
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    socket.close()
                }
            }
        }

    }

    suspend fun NoLimit(): String {
        GlobalScope.launch {
            NoLimitServer()
        }
        val socket =
            aSocket(createActorSelectorManager()).tcp().connect(NetworkAddress("127.0.0.1", 5555))
        val input = socket.openReadChannel()
        val output = socket.openWriteChannel(autoFlush = true)

        output.writeStringUtf8("hello\r\n")
        return input.readUTF8Line(Int.MAX_VALUE) ?: ""

    }
}
