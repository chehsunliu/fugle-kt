package io.github.chehsunliu.fuglekt.core

import io.github.chehsunliu.fuglekt.core.model.GetCandlesResponse
import io.github.chehsunliu.fuglekt.core.model.GetDealtsResponse
import io.github.chehsunliu.fuglekt.core.model.GetVolumesResponse
import java.io.IOException
import java.time.LocalDate
import java.util.concurrent.CompletableFuture
import java.util.zip.GZIPInputStream
import kotlinx.coroutines.future.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

internal class DefaultFugleAsyncClient(private val baseUrl: HttpUrl, private val token: String) :
    FugleAsyncClient {
  private val client = OkHttpClient()

  override suspend fun getDealts(
      symbolId: String,
      limit: Int?,
      offset: Int?,
      oddLot: Boolean?
  ): GetDealtsResponse {
    val urlBuilder =
        HttpUrl.Builder()
            .configureUrlBuilder()
            .addEncodedPathSegments("realtime/v0.3/intraday/dealts")
            .addQueryParameter("symbolId", symbolId)

    if (limit != null) {
      urlBuilder.addQueryParameter("limit", limit.toString())
    }

    if (offset != null) {
      urlBuilder.addQueryParameter("offset", offset.toString())
    }

    if (oddLot != null) {
      urlBuilder.addQueryParameter("oddLot", oddLot.toString())
    }

    val request = Request.Builder().configureRequestBuilder().get().url(urlBuilder.build()).build()
    return execute<GetDealtsResponse>(request).await()
  }

  override suspend fun getVolumes(symbolId: String, oddLot: Boolean?): GetVolumesResponse {
    val urlBuilder =
        HttpUrl.Builder()
            .configureUrlBuilder()
            .addEncodedPathSegments("realtime/v0.3/intraday/volumes")
            .addQueryParameter("symbolId", symbolId)

    if (oddLot != null) {
      urlBuilder.addQueryParameter("oddLot", oddLot.toString())
    }

    val request = Request.Builder().configureRequestBuilder().get().url(urlBuilder.build()).build()
    return execute<GetVolumesResponse>(request).await()
  }

  override suspend fun getCandles(
      symbolId: String,
      startDate: LocalDate,
      endDate: LocalDate
  ): GetCandlesResponse {
    val url =
        HttpUrl.Builder()
            .configureUrlBuilder()
            .addEncodedPathSegments("marketdata/v0.3/candles")
            .addQueryParameter("symbolId", symbolId)
            .addQueryParameter("from", startDate.toString())
            .addQueryParameter("to", endDate.toString())
            .build()

    val request = Request.Builder().configureRequestBuilder().get().url(url).build()
    return execute<GetCandlesResponse>(request).await()
  }

  private fun HttpUrl.Builder.configureUrlBuilder(): HttpUrl.Builder =
      this.scheme(baseUrl.scheme)
          .host(baseUrl.host)
          .port(baseUrl.port)
          .addQueryParameter("apiToken", token)

  private fun Request.Builder.configureRequestBuilder(): Request.Builder =
      this.addHeader("Accept", "*/*").addHeader("Accept-Encoding", "gzip")

  private inline fun <reified T> execute(request: Request): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    client
        .newCall(request)
        .enqueue(
            object : Callback {
              override fun onFailure(call: Call, e: IOException) {
                future.completeExceptionally(e)
              }

              override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                  future.completeExceptionally(FugleKtException.from(response))
                  return
                }

                val body =
                    if (response.headers("Content-Encoding").any {
                      it.equals("gzip", ignoreCase = true)
                    }) {
                      GZIPInputStream(response.body!!.byteStream())
                          .bufferedReader(Charsets.UTF_8)
                          .use { it.readText() }
                    } else {
                      response.body!!.string()
                    }

                future.complete(Json.decodeFromString<T>(body))
              }
            })

    return future
  }
}
