package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDate
import java.time.OffsetDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetMetaResponse(
    val apiVersion: String,
    val data: Data,
) {
  @Serializable
  data class Data(
      val info: Info,
      val meta: Meta,
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
  data class Meta(
      val market: String? = null,
      val nameZhTw: String,
      val industryZhTw: String? = null,
      val priceReference: Double,
      val priceHighLimit: Double? = null,
      val priceLowLimit: Double? = null,
      val canDayBuySell: Boolean? = null,
      val canDaySellBuy: Boolean? = null,
      val canShortMargin: Boolean? = null,
      val canShortLend: Boolean? = null,
      val tradingUnit: Long? = null,
      val currency: String? = null,
      val isTerminated: Boolean? = null,
      val isSuspended: Boolean? = null,
      val typeZhTw: String,
      val abnormal: String? = null,
      val isUnusuallyRecommended: Boolean? = null,
      val isNewlyCompiled: Boolean? = null,
  )
}
