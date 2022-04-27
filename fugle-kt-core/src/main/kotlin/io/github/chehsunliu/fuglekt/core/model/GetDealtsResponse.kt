package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDate
import java.time.OffsetDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetDealtsResponse(
    val apiVersion: String,
    val data: Data,
) {
  @Serializable data class Data(val info: Info, val dealts: List<Dealt>)

  @Serializable
  data class Info(
      @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
      val type: String,
      val exchange: String,
      val market: String,
      val symbolId: String,
      val countryCode: String,
      val timeZone: String,
  )

  @Serializable
  data class Dealt(
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
      val bid: Double,
      val ask: Double,
      val price: Double,
      val volume: Long,
      val serial: Long,
  )
}
