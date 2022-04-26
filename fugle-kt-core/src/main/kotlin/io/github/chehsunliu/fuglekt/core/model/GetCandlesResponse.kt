package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDate
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
      @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
      val open: Double,
      val high: Double,
      val low: Double,
      val close: Double,
      val volume: Long,
  )
}
