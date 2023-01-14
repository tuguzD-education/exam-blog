@file:Suppress("unused", "UnnecessaryOptInAnnotation")
@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd

import io.github.tuguzd.repository.*
import io.github.tuguzd.route.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.module() {
    val databaseUri = environment.config.property(
        "database.connectionUri"
    ).getString()
    val appModule = module {
        single { Driver(databaseUri) }
        singleOf(::CrudDao)
        singleOf(::BusinessDao)
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(ContentNegotiation) {
        json()
    }
    install(Locations)

    routing {
        crud()
        business()
    }
}
