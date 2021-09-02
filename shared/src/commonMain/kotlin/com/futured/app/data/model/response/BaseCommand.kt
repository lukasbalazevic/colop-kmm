package com.futured.app.data.model.response

import kotlin.jvm.JvmInline

@JvmInline
value class UInt8(val number: Byte) {
    fun toByteArray(): ByteArray = ByteArray(1)
}

@JvmInline
value class UInt16(val number: Short)

@JvmInline
value class UInt32(val number: Int)

@JvmInline
value class UInt64(val number: Long)

enum class CommandCode(val code: UInt16) {
    DeviceName(UInt16(0x02)),
    Mcui(UInt16(0x2B))
}

data class BaseCommand(
    val length: UInt32,
    val code: CommandCode,
    val sequenceNumber: UInt16
)
