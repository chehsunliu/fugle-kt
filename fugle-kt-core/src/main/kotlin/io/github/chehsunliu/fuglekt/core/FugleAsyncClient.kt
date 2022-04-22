package io.github.chehsunliu.fuglekt.core

import io.github.chehsunliu.fuglekt.core.model.GetCandlesResponse
import java.time.LocalDate
import okhttp3.HttpUrl.Companion.toHttpUrl

interface FugleAsyncClient {
  suspend fun getCandles(
      symbolId: String,
      startDate: LocalDate,
      endDate: LocalDate
  ): GetCandlesResponse

  companion object {
    fun create(token: String, url: String = "https://api.fugle.tw"): FugleAsyncClient {
      return DefaultFugleAsyncClient(baseUrl = url.toHttpUrl(), token = token)
    }
  }
}
