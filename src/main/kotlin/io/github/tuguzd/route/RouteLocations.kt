@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.ktor.server.locations.*

@Location("/blogs")
class Blogs {
    @Location("")
    data class Create(val blog: Blogs)

    @Location("")
    data class Read(val id: String, val blog: Blogs)

    @Location("")
    data class Update(val id: String, val blog: Blogs)

    @Location("")
    data class Delete(val id: String, val blog: Blogs)

    @Location("/search")
    data class SearchName(val name: String, val blog: Blogs)
}
