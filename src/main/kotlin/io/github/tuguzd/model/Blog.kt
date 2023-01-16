package io.github.tuguzd.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class Blog(
    @SerialName("_id") @Contextual val id: Id<Blog> = newId(),
    val name: String,
    val desc: String,
)
