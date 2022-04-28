package io.github.chehsunliu.fuglekt.core.model

import java.time.OffsetDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetDealtsResponse(val apiVersion: String, val data: Data) {

  @Serializable
  data class Data(
      val info: StockInfo,
      val dealts: List<Dealt>,
  )

  @Serializable
  data class Dealt(
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
      val bid: Double? = null,
      val ask: Double? = null,
      val price: Double,
      val volume: Long? = null,
      val serial: Long,
  )
}
