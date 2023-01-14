package io.github.tuguzd.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String) {
    companion object {
        val NOT_FOUND_RESPONSE = ErrorResponse("Blog was not found")
        val NOT_MODIFIED_RESPONSE = ErrorResponse("Blog was not modified")
        val BAD_REQUEST_RESPONSE = ErrorResponse("Invalid request")
    }
}
