package io.github.tuguzd.model

import kotlinx.serialization.Serializable

@Serializable
data class BlogDto(
    val id: String? = null,
    val name: String,
    val desc: String,
)

fun BlogDto.toBlog() = Blog(
    name = this.name,
    desc = this.desc,
)
