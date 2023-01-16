package io.github.tuguzd.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class BlogDto(
    val id: String? = null,
    val name: String,
    val desc: String,
    @EncodeDefault val tasks: JsonElement = JsonArray(content = listOf()),
)

fun BlogDto.toBlog() = Blog(
    name = this.name,
    desc = this.desc,
)
