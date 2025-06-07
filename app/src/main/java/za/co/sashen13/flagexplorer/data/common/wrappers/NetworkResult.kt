package za.co.sashen13.flagexplorer.data.common.wrappers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val body: T) : NetworkResult<T>()
    class Error<T : Any>(
        val code: Int,
        val message: String?,
        val errorResponse: ErrorResponse? = null
    ) : NetworkResult<T>()

    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
}

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @field:Json(name = "status")
    val status: String?,

    @field:Json(name = "message")
    val message: String,

    @field:Json(name = "data")
    val data: List<String>?
)
