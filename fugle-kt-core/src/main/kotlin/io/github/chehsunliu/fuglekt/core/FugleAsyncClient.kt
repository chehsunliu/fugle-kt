package io.github.chehsunliu.fuglekt.core

import io.github.chehsunliu.fuglekt.core.model.GetCandlesResponse
import io.github.chehsunliu.fuglekt.core.model.GetChartResponse
import io.github.chehsunliu.fuglekt.core.model.GetDealtsResponse
import io.github.chehsunliu.fuglekt.core.model.GetMetaResponse
import io.github.chehsunliu.fuglekt.core.model.GetQuoteResponse
import io.github.chehsunliu.fuglekt.core.model.GetVolumesResponse
import java.io.Closeable
import java.time.LocalDate
import okhttp3.HttpUrl.Companion.toHttpUrl

interface FugleAsyncClient : Closeable {
  suspend fun getMeta(symbolId: String, oddLot: Boolean? = null): GetMetaResponse

  suspend fun getQuote(symbolId: String, oddLot: Boolean? = null): GetQuoteResponse

  suspend fun getChart(symbolId: String, oddLot: Boolean? = null): GetChartResponse

  suspend fun getDealts(
      symbolId: String,
      limit: Int? = null,
      offset: Int? = null,
      oddLot: Boolean? = null
  ): GetDealtsResponse

  suspend fun getVolumes(symbolId: String, oddLot: Boolean? = null): GetVolumesResponse

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
