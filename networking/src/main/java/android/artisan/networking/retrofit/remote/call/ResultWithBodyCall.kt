package android.artisan.networking.retrofit.remote.call

import android.artisan.foundation.helper.executeSafely
import android.artisan.foundation.model.Result
import android.artisan.foundation.model.Result.Failure
import android.artisan.networking.retrofit.remote.mapper.toInternetConnectionExceptionOrSelf
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
import java.lang.reflect.Type

/**
 * A custom [Call] implementation that wraps another [Call] and transforms its result
 * into a [Result] type, handling potential exceptions and ensuring proper coroutine
 * context management.
 *
 * This class is designed to be used with Retrofit to seamlessly integrate with
 * [Result] type for handling both successful and error responses. It
 * provides asynchronous execution via [enqueue] and synchronous execution via
 * [execute], similar to the standard Retrofit [Call].
 *
 * @param T The type of the response body.
 * @property proxy The original [Call] instance being wrapped.
 * @property resultRawType The raw type of the expected result (e.g., `User::class.java`, `Unit::class.java`).
 *                        This is used to handle cases where the result type is `Unit`.
 * @property coroutineScope The [CoroutineScope] in which asynchronous operations should be launched.
 *
 * @suppress TooGenericExceptionCaught
 */
@Suppress("TooGenericExceptionCaught")
internal class ResultWithBodyCall<T : Any>(
    private val proxy: Call<T>,
    private val resultRawType: Type,
    private val coroutineScope: CoroutineScope,
) : Call<Result<T?>> {

    override fun enqueue(callback: Callback<Result<T?>>) {
        coroutineScope.launch {
            try {
                val response = proxy.awaitResponse()
                callback.onResponse(
                    this@ResultWithBodyCall,
                    Response.success(response.toResult(resultRawType))
                )
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                callback.onResponse(
                    this@ResultWithBodyCall,
                    Response.success(Failure(e.toInternetConnectionExceptionOrSelf()))
                )
            }
        }
    }

    override fun execute(): Response<Result<T?>> =
        runBlocking(coroutineScope.coroutineContext) {
            try {
                Response.success(proxy.execute().toResult(resultRawType))
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Response.success(Failure(e.toInternetConnectionExceptionOrSelf()))
            }
        }

    override fun clone(): Call<Result<T?>> =
        ResultWithBodyCall(proxy.clone(), resultRawType, coroutineScope)

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() = proxy.cancel()

    /**
     * Converts a Retrofit [Response] to a [Result].
     *
     * This function handles both successful and unsuccessful responses.
     *
     * For successful responses:
     * - If the expected result type is `Unit`, it returns `Result.success(Unit as T)`.
     * - Otherwise, it returns `Result.success(response.body())`.
     *
     * For unsuccessful responses:
     * - It throws an [HttpException] wrapping the original response.
     *
     * This function is marked as `suspend` and is intended to be used within a coroutine scope.
     * It leverages the [executeSafely] extension function to encapsulate the execution within a `Result`.
     *
     * @param T The type of the expected result. Can be nullable.
     * @param resultRawType The raw type of the expected result. Used to handle `Unit` specifically.
     * @return A [Result] object containing either the successful response body (or Unit) or an exception.
     * @throws HttpException If the response is not successful (e.g., 404, 500).
     *
     * @see executeSafely
     */
    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any?> Response<T>.toResult(resultRawType: Type): Result<T?> {
        return executeSafely {
            if (isSuccessful) {
                if (resultRawType == Unit::class.java) {
                    Unit as T
                } else {
                    body()
                }
            } else {
                throw HttpException(this)
            }
        }
    }
}
