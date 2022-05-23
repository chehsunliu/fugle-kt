package io.github.chehsunliu.fuglekt.core.model

import java.time.OffsetDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetQuoteResponse(val apiVersion: String, val data: Data) {

  @Serializable
  data class Data(
      val info: StockInfo,
      val quote: Quote,
  )

  @Serializable
  data class Quote(
      val isCurbing: Boolean? = null,
      val isCurbingFall: Boolean? = null,
      val isCurbingRise: Boolean? = null,
      val isTrial: Boolean? = null,
      val isOpenDelayed: Boolean? = null,
      val isCloseDelayed: Boolean? = null,
      val isHalting: Boolean? = null,
      val isClosed: Boolean,
      val isDealt: Boolean? = null,
      val total: Total? = null,
      val trial: Trial? = null,
      val trade: Trade? = null,
      val order: Order? = null,
      val priceHigh: Price? = null,
      val priceLow: Price? = null,
      val priceOpen: Price? = null,
      val priceAvg: Price? = null,
      val change: Double,
      val changePercent: Double,
      val amplitude: Double,
      val priceLimit: Int? = null,
  )

  @Serializable
  data class Total(
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
      val transaction: Long,
      val tradeValue: Double,
      val tradeVolume: Long,
      val tradeVolumeAtBid: Long,
      val tradeVolumeAtAsk: Long,
      val bidOrders: Long? = null,
      val askOrders: Long? = null,
      val bidVolume: Long? = null,
      val askVolume: Long? = null,
      val serial: Long,
  )

  @Serializable
  data class Trial(
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
      val bid: Double,
      val ask: Double,
      val price: Double,
      val volume: Long,
      val serial: Long,
  )

  @Serializable
  data class Trade(
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
      val bid: Double? = null,
      val ask: Double? = null,
      val price: Double,
      val volume: Long? = null,
      val serial: Long,
  )

  @Serializable
  data class Order(
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
      val bids: List<BidAsk>,
      val asks: List<BidAsk>,
  )

  @Serializable
  data class BidAsk(
      val price: Double,
      val volume: Long,
  )

  @Serializable
  data class Price(
      val price: Double,
      @Serializable(with = OffsetDateTimeSerializer::class) val at: OffsetDateTime,
  )
}
