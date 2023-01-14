package io.github.tuguzd

import io.github.tuguzd.model.BlogDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateTest {
    private val blogJet =
        BlogDto(name = "Jet", desc = "Brains")
    private val blogJot =
        BlogDto(name = "Jot", desc = "Brains")

    @Test
    fun updateBlog() = testSetup { client ->
        assertEquals(HttpStatusCode.Created, client.post(blogJet).status)

        var list: List<BlogDto> = Json.decodeFromString(
            client.get("/blogs").bodyAsText()
        )
        var blog = list.find { it.name == blogJet.name && it.desc == blogJet.desc }
        assert(blog != null)

        val id = blog?.id
        assertEquals(HttpStatusCode.Accepted, client.put(blogJot, id).status)

        list = Json.decodeFromString(
            client.get("/blogs?name=Jot").bodyAsText()
        )
        blog = list.find { it.name == blogJot.name && it.desc == blogJot.desc }
        assert(blog != null)

        client.delete("/blogs?id=$id")
        val response = client.get("/blogs")
        assertEquals("[]", response.bodyAsText())
    }
}
