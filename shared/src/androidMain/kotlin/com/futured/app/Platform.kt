package com.futured.app

import android.content.Context
import com.futured.app.db.CommonDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.selector.SelectorManager
import kotlinx.coroutines.Dispatchers

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

lateinit var appContext: Context


actual fun createDriver(): SqlDriver =
     AndroidSqliteDriver(CommonDatabase.Schema, appContext, "commondb.db")

actual fun createActorSelectorManager(): SelectorManager = ActorSelectorManager(Dispatchers.IO)