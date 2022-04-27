package io.github.chehsunliu.fuglekt.core

import io.github.chehsunliu.fuglekt.core.model.GetCandlesResponse
import io.github.chehsunliu.fuglekt.core.model.GetDealtsResponse
import io.github.chehsunliu.fuglekt.core.model.GetMetaResponse
import io.github.chehsunliu.fuglekt.core.model.GetVolumesResponse
import java.time.LocalDate
import okhttp3.HttpUrl.Companion.toHttpUrl

interface FugleAsyncClient {
  suspend fun getMeta(symbolId: String, oddLot: Boolean? = null): GetMetaResponse

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
