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
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun Application.module() {
    val databaseUri = environment.config.property("database.connectionUri").getString()
    val tasksUri = environment.config.property("service.tasksUrl").getString()
    val appModule = module {
        single { Driver(databaseUri) }
        single { Client(tasksUri) }
        singleOf(::BlogDao)
        singleOf(::TaskDao)
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(ContentNegotiation) {
        val json = Json {
            serializersModule = IdKotlinXSerializationModule
        }
        json(json)
    }
    install(Locations)

    routing {
        crud()
        business()
    }
}
