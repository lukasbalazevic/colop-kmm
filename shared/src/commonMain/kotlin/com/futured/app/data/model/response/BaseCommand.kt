package com.futured.app.data.model.response

import kotlinx.serialization.Serializable

value class UInt8(private val number: Int)
value class UInt16(private val number: Int)
value class UInt32(private val number: Int)
value class UInt64(private val number: Long)

enum class CommandCode(val code: UInt16) {
    DeviceName(UInt16(0x02)),
    Mcui(UInt16(0x2B))
}

data class BaseCommand(
    val length: UInt32,
    val code: CommandCode,
    val sequenceNumber: UInt16
)

{
    companion object {
        fun parse() {
        }
    }
}
