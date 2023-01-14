package io.github.tuguzd.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Blog(
    @BsonId val id: Id<Blog>? = null,
    val name: String,
    val desc: String,
)

fun Blog.toDto() = BlogDto(
    id = this.id.toString(),
    name = this.name,
    desc = this.desc,
)
