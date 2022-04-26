package io.github.chehsunliu.fuglekt.core

import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class FugleAsyncClientIntegrationTest {
  private val client = FugleAsyncClient.create(token = System.getenv("FUGLE_TOKEN"))

  @Test
  fun `getting candles should work`() = runBlocking {
    val response =
        client.getCandles(
            symbolId = "0050",
            startDate = LocalDate.of(2022, 4, 18),
            endDate = LocalDate.of(2022, 4, 22))

    assertAll(
        { assertEquals("0050", response.symbolId) },
        { assertEquals("EQUITY", response.type) },
        { assertEquals("TWSE", response.exchange) },
        { assertEquals("TSE", response.market) },
        { assertEquals(5, response.candles.size) })

    val candle0 = response.candles[0]

    assertAll(
        { assertEquals(LocalDate.of(2022, 4, 22), candle0.date) },
        { assertEquals(131.4, candle0.open) },
        { assertEquals(131.75, candle0.high) },
        { assertEquals(130.85, candle0.low) },
        { assertEquals(131.6, candle0.close) },
        { assertEquals(12398307L, candle0.volume) })
  }
}
