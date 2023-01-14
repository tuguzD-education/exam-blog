package io.github.tuguzd

import io.github.tuguzd.model.BlogDto
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testHealth() = testApplication {
        val response = client.get("/health")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Healthy", response.bodyAsText())
    }

    @Test
    fun testCrudBlog() = testApplication {
        application {
            module()
        }
        environment {
            config = MapApplicationConfig(
                "database.connectionUri" to "mongodb://mongo:password@localhost:27017/?authSource=admin"
            )
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        var response = client.get("/blogs")
        assertEquals("[]", response.bodyAsText())

        val blog = BlogDto(name = "Jet", desc = "Brains")

        response = client.post("/blogs") {
            contentType(ContentType.Application.Json)
            setBody(blog)
        }
        assertEquals(HttpStatusCode.Created, response.status)

        response = client.get("/blogs")
        val list: List<BlogDto> = Json.decodeFromString(response.bodyAsText())
        assert(list.find { it.name == blog.name && it.desc == blog.desc } != null)
    }
}
