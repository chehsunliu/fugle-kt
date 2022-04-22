package io.github.chehsunliu.fuglekt.core.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCandlesResponse(
    val symbolId: String,
    val type: String,
    val exchange: String,
    val market: String,
    val candles: List<Candle>,
) {
  @Serializable
  data class Candle(
      val date: String,
      val open: Double,
      val high: Double,
      val low: Double,
      val close: Double,
      val volume: Long,
  )
}
