package io.github.tuguzd.repository

import com.mongodb.client.model.IndexOptions
import io.github.tuguzd.model.Blog
import io.github.tuguzd.model.BlogDto
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.*

class Driver(uri: String) {
    private val client = KMongo.createClient(uri).coroutine
    private val database = client.getDatabase("blog")

    val collection = database.getCollection<Blog>()

    init {
        runBlocking {
            collection.ensureIndex(
                properties = arrayOf(BlogDto::name),
                indexOptions = IndexOptions().unique(true),
            )
        }
    }
}
