@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.Blog
import io.github.tuguzd.model.toDto
import io.github.tuguzd.repository.BusinessDao
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.business() {
    val dao by inject<BusinessDao>()

    get<Blogs> {
        call.respond(
            dao.readAll().map(Blog::toDto)
        )
    }

    get<Blogs.SearchName> {
        call.respond(
            dao.readName(it.name).map(Blog::toDto)
        )
    }

    get("/health") {
        call.respond("Healthy")
    }
}
