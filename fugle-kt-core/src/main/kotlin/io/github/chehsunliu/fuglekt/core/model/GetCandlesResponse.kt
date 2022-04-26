package io.github.chehsunliu.fuglekt.core.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

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

