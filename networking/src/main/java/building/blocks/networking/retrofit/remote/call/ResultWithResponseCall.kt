package building.blocks.networking.retrofit.remote.call

import building.blocks.foundation.helper.executeSafely
import building.blocks.networking.retrofit.remote.mapper.toInternetConnectionExceptionOrSelf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse

/**
 * A Retrofit [Call] implementation that wraps another [Call] and transforms its result
 * into a [Result] of a [Response] object. This class handles both successful and failed
 * responses, including exceptions, and encapsulates them within a [Result].
 *
 * It provides a convenient way to handle network operations and their outcomes in a consistent
 * manner, particularly when working with Kotlin coroutines.
 *
 * @param T The type of the data contained within the response.
 * @property proxy The underlying [Call] instance that this class wraps. It's responsible for
 *      making the actual network request.
 * @property coroutineScope The [CoroutineScope] in which the asynchronous operations will be
 *      launched. This scope determines the lifecycle and context of the coroutines.
 */
@Suppress("TooGenericExceptionCaught")
internal class ResultWithResponseCall<T : Any>(
    private val proxy: Call<T?>,
    private val coroutineScope: CoroutineScope,
) : Call<Result<Response<T?>>> {

    override fun enqueue(callback: Callback<Result<Response<T?>>>) {
        coroutineScope.launch {
            try {
                val response = proxy.awaitResponse()
                callback.onResponse(
                    this@ResultWithResponseCall,
                    Response.success(response.toResult())
                )
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                callback.onResponse(
                    this@ResultWithResponseCall,
                    Response.success(Result.failure(e.toInternetConnectionExceptionOrSelf()))
                )
            }
        }
    }

    override fun execute(): Response<Result<Response<T?>>> =
        runBlocking(coroutineScope.coroutineContext) {
            try {
                Response.success(proxy.execute().toResult())
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Response.success(Result.failure(e.toInternetConnectionExceptionOrSelf()))
            }
        }

    override fun clone(): Call<Result<Response<T?>>> =
        ResultWithResponseCall(proxy.clone(), coroutineScope)

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() = proxy.cancel()

    /**
     * Converts a Retrofit [Response] to a [Result] object, handling success and error cases.
     *
     * This function takes a suspendable Retrofit [Response] and wraps it in a [Result] object.
     * If the response is successful (HTTP status code in the 200-299 range), it returns a [Result.success]
     * containing the original [Response]. If the response is not successful, it throws an [HttpException]
     * which will be caught by the [executeSafely] block and converted to a [Result.failure].
     *
     * This allows for consistent error handling using Kotlin's [Result] type.
     *
     * @param T The type of the data contained within the response body. Can be nullable.
     * @return A [Result] containing the original [Response] on success, or an [HttpException] on failure.
     *
     * @throws HttpException If the HTTP response is not successful (status code outside 200-299 range). This exception
     *                      is thrown directly within this function, but will be wrapped in the Result.failure by
     *                      the executeSafely function.
     * @see executeSafely
     */
    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> Response<T?>.toResult(): Result<Response<T?>> {
        return executeSafely {
            if (isSuccessful) {
                this
            } else {
                throw HttpException(this)
            }
        }
    }
}
