package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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

internal object LocalDateSerializer : KSerializer<LocalDate> {
  override val descriptor: SerialDescriptor =
      PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: LocalDate) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): LocalDate {
    return LocalDate.parse(decoder.decodeString())
  }
}
