package io.github.tuguzd

import io.github.tuguzd.model.BlogDto
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateTest {
    private var testBlog =
        BlogDto(name = "Jet", desc = "Brains")

    @Test
    fun createBlog() = testSetup { client ->
        assertEquals(HttpStatusCode.Created, client.post(testBlog).status)

        val list: List<BlogDto> = Json.decodeFromString(
            client.get("/blogs").bodyAsText()
        )
        val item = list.find {
            it.name == testBlog.name && it.desc == testBlog.desc
        }
        assert(item != null)

        var response = client.delete("/blogs?id=${item?.id}")
        assertEquals(HttpStatusCode.OK, response.status)

        response = client.get("/blogs")
        assertEquals("[]", response.bodyAsText())
    }
}
