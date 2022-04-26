package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDate
import java.time.OffsetDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetVolumesResponse(val apiVersion: String, val data: Data) {
  @Serializable
  data class Data(
      val info: Info,
      val volumes: List<Volume>,
  )

  @Serializable
  data class Info(
      @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
      val type: String,
      val exchange: String,
      val market: String,
      val symbolId: String,
      val countryCode: String,
      val timeZone: String,
      @Serializable(with = OffsetDateTimeSerializer::class) val lastUpdatedAt: OffsetDateTime,
  )

  @Serializable
  data class Volume(
      val price: Double,
      val volume: Long,
  )
}
