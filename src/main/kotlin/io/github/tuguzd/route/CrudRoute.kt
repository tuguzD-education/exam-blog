@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.*
import io.github.tuguzd.repository.BlogDao
import io.github.tuguzd.repository.TaskDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import java.net.ConnectException

fun Route.crud() {
    val blogDao by inject<BlogDao>()
    val taskDao by inject<TaskDao>()

    suspend fun Blog.toDto() = BlogDto(
        id = id.toString(),
        name = name,
        desc = desc,
        tasks = taskDao.findByBlogId(id.toString()),
    )

    post<Blogs.Create> {
        blogDao.create(call.receive<BlogDto>().toBlog())
            ?.let { id ->
                call.apply {
//                    response.headers.append("My-User-Id-Header", id.toString())
                    respond(HttpStatusCode.Created)
                }
            } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
    }

    get<Blogs.Read> {
        try {
            blogDao.readId(it.id)
                ?.let { blog -> call.respond(blog.toDto()) }
                ?: call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
        } catch (e: ConnectException) {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse.SERVICE_UNAVAILABLE)
        }
    }

    put<Blogs.Update> {
        if (blogDao.update(it.id, call.receive<BlogDto>().toBlog()))
            call.respond(HttpStatusCode.Accepted)
        else call.respond(HttpStatusCode.NotModified, ErrorResponse.NOT_MODIFIED_RESPONSE)
    }

    delete<Blogs.Delete> {
        if (blogDao.delete(it.id))
            call.respond(HttpStatusCode.OK)
        else call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
    }
}
