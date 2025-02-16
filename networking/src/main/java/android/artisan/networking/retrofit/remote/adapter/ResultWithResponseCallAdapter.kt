package android.artisan.networking.retrofit.remote.adapter

import android.artisan.foundation.model.Result
import android.artisan.networking.retrofit.remote.call.ResultWithResponseCall
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.lang.reflect.Type

internal class ResultWithResponseCallAdapter(
    private val resultType: Type,
    private val coroutineScope: CoroutineScope,
) : CallAdapter<Type, Call<Result<Response<Type?>>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type?>): Call<Result<Response<Type?>>> =
        ResultWithResponseCall(call, coroutineScope)
}
