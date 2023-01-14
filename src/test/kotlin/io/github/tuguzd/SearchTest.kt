package io.github.tuguzd

import io.github.tuguzd.model.BlogDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class SearchTest {
    private val blogJet =
        BlogDto(name = "Jet", desc = "Brains")
    private val blogJot =
        BlogDto(name = "Jot", desc = "Brains")

    @Test
    fun searchBlog() = testSetup { client ->
        assertEquals(
            HttpStatusCode.Created, client.post(blogJet).status
        )
        assertEquals(
            HttpStatusCode.Created, client.post(blogJot).status
        )

        var list: List<BlogDto> = Json.decodeFromString(
            client.get("/blogs?name=${blogJet.name[1]}").bodyAsText()
        )
        var blog = list.find { it.name == blogJet.name && it.desc == blogJet.desc }
        assert(blog != null)

        client.delete("/blogs?id=${blog?.id}")
        assertNotEquals("[]", client.get("/blogs").bodyAsText())

        list = Json.decodeFromString(
            client.get("/blogs?name=J").bodyAsText()
        )
        blog = list.find { it.name == blogJot.name && it.desc == blogJot.desc }
        assert(blog != null)

        client.delete("/blogs?id=${blog?.id}")
        assertEquals("[]", client.get("/blogs").bodyAsText())
    }
}
