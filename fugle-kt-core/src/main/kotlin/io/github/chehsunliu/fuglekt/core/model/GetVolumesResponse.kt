package io.github.chehsunliu.fuglekt.core.model

import kotlinx.serialization.Serializable

@Serializable
data class GetVolumesResponse(val apiVersion: String, val data: Data) {

  @Serializable
  data class Data(
      val info: StockInfo,
      val volumes: List<Volume>,
  )

  @Serializable
  data class Volume(
      val price: Double,
      val volume: Long,
  )
}
