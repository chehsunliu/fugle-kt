package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetChartResponse(val apiVersion: String, val data: Data) {

  @Serializable data class Data(val info: StockInfo, val chart: Chart)

  @Serializable
  data class Chart(
      val a: List<Double>? = null,
      val o: List<Double>,
      val h: List<Double>,
      val l: List<Double>,
      val c: List<Double>,
      val v: List<Long>,
      @SerialName("t")
      val timestamps: List<@Serializable(with = LocalDateTimeSerializer::class) LocalDateTime>,
  )
}
