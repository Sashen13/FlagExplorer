package za.co.sashen13.flagexplorer.data.common.wrappers

import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            val errorResponse = convertErrorBody(response)
            NetworkResult.Error(
                code = response.code(),
                message = errorResponse?.message ?: response.message(),
                errorResponse = errorResponse
            )
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}

private fun <T> convertErrorBody(response: Response<T>): ErrorResponse? {
    return try {
        response.errorBody()?.let {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(ErrorResponse::class.java)
            adapter.fromJson(it.source())
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}