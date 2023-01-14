package io.github.tuguzd

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals

class InitTest {
    @Test
    fun health() = testSetup { client ->
        val response = client.get("/health")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Healthy", response.bodyAsText())
    }

    @Test
    fun emptyBlog() = testSetup { client ->
        val response = client.get("/blogs")
        assertEquals("[]", response.bodyAsText())
    }
}
