package io.github.tuguzd.repository

import io.github.tuguzd.model.Blog
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.regex

class BusinessDao : KoinComponent {
    private val driver by inject<Driver>()
    private val collection = driver.collection

    fun readAll(): List<Blog> =
        collection.find().toList()

    fun readName(name: String): List<Blog> =
        collection.find(
            (Blog::name).regex(name, "i")
        ).toList()
}
