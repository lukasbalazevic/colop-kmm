package com.futured.app.data.api

import com.futured.app.createActorSelectorManager
import io.ktor.client.statement.HttpResponse
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.network.NetworkAddress
import io.ktor.utils.io.writeFully

internal class RestApiManager {

    companion object {
        fun randomData(): ByteArray = listOf(
            RickAstley
        ).map { it.map { it.toByte() }.toTypedArray().toByteArray() }.random()
    }

    suspend fun NoLimit(): String {
        val socket =
            aSocket(createActorSelectorManager()).tcp().connect(NetworkAddress("192.168.1.1", 5002))
        val input = socket.openReadChannel()
        val output = socket.openWriteChannel(autoFlush = true)

        val data = randomData()

        output.writeFully(data)
        return input.readByte().toString()

    }
}
