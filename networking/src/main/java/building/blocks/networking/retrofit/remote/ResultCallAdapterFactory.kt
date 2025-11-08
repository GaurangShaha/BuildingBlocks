package building.blocks.networking.retrofit.remote

import building.blocks.networking.retrofit.remote.adapter.ResultWithBodyCallAdapter
import building.blocks.networking.retrofit.remote.adapter.ResultWithResponseCallAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A [CallAdapter.Factory] that handles `Call<Result<T>>` and `Call<Result<Response<T>>>` return types,
 * transforming them into a `Result` object representing the outcome of the network operation.
 *
 * This factory allows you to wrap network responses in a `Result` type, which can be either a
 * `Result.Success` containing the successful data or a `Result.Failure` containing the exception
 * that occurred. This simplifies error handling and allows for more concise code when dealing
 * with network requests.
 *
 * The `Result` object is then converted into [Result.Success] or [Result.Failure] according to the response from
 * the server.
 *
 * It supports two main return types:
 *   - `Call<Result<T>>`:  For cases where you want the `Result` to contain the response body `T` directly.
 *   - `Call<Result<Response<T>>>`: For cases where you need the full `Response` object (including headers,
 *   status code, etc.) in the `Result`.
 *
 * This factory uses a given [CoroutineScope] to handle background tasks related to the execution of the network call.
 *
 * Usage:
 *
 * ```kotlin
 *          Retrofit.Builder().baseUrl(BASE_URL)
 *             .addConverterFactory(MoshiConverterFactory.create())
 *             .addCallAdapterFactory(ResultCallAdapterFactory.create())
 *             .client(OkHttpClient.Builder().build()).build()
 * ```
 */
public class ResultCallAdapterFactory private constructor(
    private val coroutineScope: CoroutineScope,
) : CallAdapter.Factory() {

    /**
     * Creates a call adapter for the given [returnType].
     * Retrofit internally call this function to get appropriate adapter.
     */
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(callType)
        if (rawType != Result::class.java) {
            return null
        }

        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
        val resultRawType = getRawType(resultType)

        return if (resultRawType == Response::class.java) {
            val responseType = getParameterUpperBound(0, resultType as ParameterizedType)
            ResultWithResponseCallAdapter(
                resultType = responseType,
                coroutineScope = coroutineScope
            )
        } else {
            ResultWithBodyCallAdapter(
                resultType = resultType,
                resultRawType = resultRawType,
                coroutineScope = coroutineScope
            )
        }
    }

    public companion object {
        /**
         * Creates a [ResultCallAdapterFactory] instance.
         *
         * This factory is used to adapt Retrofit's call to return a [Result] type,
         * encapsulating the success or failure of the network operation. This simplifies
         * error handling and makes it easier to work with the result of network requests.
         *
         * @param coroutineScope The coroutine scope to use for executing the network request.
         *                       Defaults to a new [CoroutineScope] with the [Dispatchers.IO] dispatcher.
         *                       You can provide a custom scope for better control over the coroutines' lifecycle.
         * @return A new [ResultCallAdapterFactory] instance.
         */
        public fun create(
            coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        ): ResultCallAdapterFactory = ResultCallAdapterFactory(coroutineScope = coroutineScope)
    }
}
