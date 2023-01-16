package io.github.tuguzd.repository

import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TaskDao : KoinComponent {
    private val client by inject<Client>()

    suspend fun findByBlogId(blogId: String): JsonElement =
        client.httpClient.get(Tasks.Filter(blogId = blogId)).body()

    @Resource("/")
    private class Tasks {
        @Resource("")
        data class Filter(
            val task: Tasks = Tasks(),
            @SerialName("blog_id") val blogId: String? = null,
        )
    }
}
