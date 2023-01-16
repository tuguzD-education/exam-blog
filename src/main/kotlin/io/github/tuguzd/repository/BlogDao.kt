package io.github.tuguzd.repository

import io.github.tuguzd.model.Blog
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId
import org.litote.kmongo.regex

class BlogDao : KoinComponent {
    private val driver by inject<Driver>()
    private val collection = driver.collection

    suspend fun readAll(): List<Blog> =
        collection.find().toList()

    suspend fun readName(name: String): List<Blog> =
        collection.find(
            Blog::name.regex(name, "i")
        ).toList()

    suspend fun create(blog: Blog): Id<Blog>? {
        collection.insertOne(blog)
        return blog.id
    }

    suspend fun readId(id: String): Blog? =
        collection.findOne(Blog::id eq ObjectId(id).toId())

    suspend fun update(id: String, request: Blog): Boolean =
        readId(id)?.let { blog ->
            collection.replaceOne(
                blog.copy(
                    name = request.name,
                    desc = request.desc,
                )
            ).modifiedCount == 1L
        } ?: false

    suspend fun delete(id: String): Boolean =
        collection.deleteOneById(ObjectId(id)).deletedCount == 1L
}
