package io.github.chehsunliu.fuglekt.core

import io.github.chehsunliu.fuglekt.core.model.GetCandlesResponse
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

  override suspend fun getCandles(
      symbolId: String,
      startDate: LocalDate,
      endDate: LocalDate
  ): GetCandlesResponse {
    val url =
        HttpUrl.Builder()
            .configureUrlBuilder()
            .addPathSegment("/marketdata/v0.3/candles")
            .addQueryParameter("symbolId", symbolId)
            .addQueryParameter("from", startDate.toString())
            .addQueryParameter("to", endDate.toString())
            .build()

    val request = Request.Builder().configureRequestBuilder().get().url(url).build()

    val future = CompletableFuture<GetCandlesResponse>()
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

                future.complete(Json.decodeFromString<GetCandlesResponse>(body))
              }
            })

    return future.await()
  }

  private fun HttpUrl.Builder.configureUrlBuilder(): HttpUrl.Builder =
      this.scheme(baseUrl.scheme)
          .host(baseUrl.host)
          .port(baseUrl.port)
          .addQueryParameter("apiToken", token)

  private fun Request.Builder.configureRequestBuilder(): Request.Builder =
      this.addHeader("Accept", "*/*").addHeader("Accept-Encoding", "gzip")
}
