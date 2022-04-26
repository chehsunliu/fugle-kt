package io.github.chehsunliu.fuglekt.core.model

import java.time.LocalDate
import java.time.OffsetDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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

internal object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
  override val descriptor: SerialDescriptor =
      PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: OffsetDateTime) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): OffsetDateTime {
    return OffsetDateTime.parse(decoder.decodeString())
  }
}
