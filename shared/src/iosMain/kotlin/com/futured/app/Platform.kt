package com.futured.app

import platform.UIKit.UIDevice
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.futured.app.db.CommonDatabase
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.TcpSocketBuilder
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun createDriver(): SqlDriver = NativeSqliteDriver(CommonDatabase.Schema, "commondb.db")
@OptIn(InternalAPI::class)
actual fun createActorSelectorManager(): SelectorManager = SelectorManager(Dispatchers.Default)
