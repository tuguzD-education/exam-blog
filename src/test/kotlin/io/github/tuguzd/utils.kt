package io.github.tuguzd

import io.github.tuguzd.model.BlogDto
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*

inline fun testSetup(
    crossinline f: suspend (HttpClient) -> Unit,
) = testApplication {
    application {
        module()
    }
    environment {
        config = MapApplicationConfig(
            "database.connectionUri" to
                    "mongodb://mongo:password@localhost:27017/?authSource=admin"
        )
    }
    val client = createClient {
        install(ContentNegotiation) {
            json()
        }
    }
    f(client)
}

suspend fun HttpClient.post(blog: BlogDto) =
    post("/blogs") {
        contentType(ContentType.Application.Json)
        setBody(blog)
    }

suspend fun HttpClient.put(blog: BlogDto, id: String?) =
    put("/blogs?id=$id") {
        contentType(ContentType.Application.Json)
        setBody(blog)
    }
