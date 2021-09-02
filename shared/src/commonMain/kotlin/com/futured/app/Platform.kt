package com.futured.app

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.network.selector.SelectorManager

expect class Platform() {
    val platform: String
}

expect fun createDriver(): SqlDriver
expect fun createActorSelectorManager(): SelectorManager
