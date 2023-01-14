@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.*
import io.github.tuguzd.repository.CrudDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.crud() {
    val dao by inject<CrudDao>()

    post<Blogs.Create> {
        dao.create(call.receive<BlogDto>().toBlog())
            ?.let { id ->
                call.apply {
//                    response.headers.append("My-User-Id-Header", id.toString())
                    respond(HttpStatusCode.Created)
                }
            } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
    }

    get<Blogs.Read> {
        dao.readId(it.id)
            ?.let { blog -> call.respond(blog.toDto()) }
            ?: call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
    }

    put<Blogs.Update> {
        if (dao.update(it.id, call.receive<BlogDto>().toBlog()))
            call.respond(HttpStatusCode.Accepted)
        else call.respond(HttpStatusCode.NotModified, ErrorResponse.NOT_MODIFIED_RESPONSE)
    }

    delete<Blogs.Delete> {
        if (dao.delete(it.id))
            call.respond(HttpStatusCode.OK)
        else call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
    }
}
