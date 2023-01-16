@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.Blog
import io.github.tuguzd.model.BlogDto
import io.github.tuguzd.model.ErrorResponse
import io.github.tuguzd.repository.BlogDao
import io.github.tuguzd.repository.TaskDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.net.ConnectException

fun Route.business() {
    val blogDao by inject<BlogDao>()
    val taskDao by inject<TaskDao>()

    suspend fun Blog.toDto() = BlogDto(
        id = id.toString(),
        name = name,
        desc = desc,
        tasks = taskDao.findByBlogId(id.toString()),
    )

    get<Blogs> {
        try {
            val blogs = blogDao.readAll().map { blog -> blog.toDto() }
            call.respond(blogs)
        } catch (e: ConnectException) {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse.SERVICE_UNAVAILABLE)
        }
    }

    get<Blogs.SearchName> {
        try {
            val blogs = blogDao.readName(it.name).map { blog -> blog.toDto() }
            call.respond(blogs)
        } catch (e: ConnectException) {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse.SERVICE_UNAVAILABLE)
        }
    }

    get("/health") {
        call.respond("Healthy")
    }
}
