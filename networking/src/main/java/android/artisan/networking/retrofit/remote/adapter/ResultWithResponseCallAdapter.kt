package android.artisan.networking.retrofit.remote.adapter

import android.artisan.foundation.model.Result
import android.artisan.networking.retrofit.remote.call.ResultWithResponseCall
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A [CallAdapter] that adapts a Retrofit [Call] to return a [Call] of [Result] containing a [Response].
 *
 * This adapter is designed to handle API calls where you want to receive both the success/failure
 * status of the call and the HTTP response details, even in case of failure. It wraps the regular
 * [Response] within a [Result] to facilitate standard Kotlin error handling patterns.
 *
 * The [Result] will be:
 * - `Result.success(Response)`: If the HTTP request was successful (2xx range).
 * - `Result.failure(Throwable)`: If the request failed due to network issues or non-2xx status code.
 * The throwable within the failure case will either be:
 *     - `HttpException` if the HTTP response code was not in the 2xx range. You can access the `response()`
 *     property of HttpException to inspect the non-successful response.
 *     - An exception related to a network or other issue if the request failed to complete.
 *
 * This adapter is particularly useful when you need fine-grained control over error handling,
 * such as when you need to inspect non-2xx HTTP responses (e.g., 400 Bad Request, 500 Internal Server Error)
 * or when you need to differentiate between network errors and HTTP errors.
 *
 * @param resultType The type of the response body that will be returned inside the [Response].
 * @param coroutineScope The [CoroutineScope] to use for launching the asynchronous task related to the
 * ResultWithResponseCall.
 */
internal class ResultWithResponseCallAdapter(
    private val resultType: Type,
    private val coroutineScope: CoroutineScope,
) : CallAdapter<Type, Call<Result<Response<Type?>>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type?>): Call<Result<Response<Type?>>> =
        ResultWithResponseCall(call, coroutineScope)
}
