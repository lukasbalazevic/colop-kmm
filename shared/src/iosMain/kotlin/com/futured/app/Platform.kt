package com.futured.app

import platform.UIKit.UIDevice
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.futured.app.db.CommonDatabase
import com.squareup.sqldelight.db.SqlDriver

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun createDriver(): SqlDriver = NativeSqliteDriver(CommonDatabase.Schema, "commondb.db")
