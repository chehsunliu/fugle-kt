package io.github.chehsunliu.fuglekt.core

import java.time.LocalDate
import kotlin.math.abs
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class FugleAsyncClientIntegrationTest {
  private val client = FugleAsyncClient.create(token = System.getenv("FUGLE_TOKEN"))

  @Test
  fun `getting meta should work`() =
      runBlocking<Unit> {
        val deferred0 = async { client.getMeta(symbolId = "2884") }
        val deferred1 = async { client.getMeta(symbolId = "0050") }
        val deferred2 = async { client.getMeta(symbolId = "0050", oddLot = true) }
        val deferred3 = async { client.getMeta(symbolId = "046500") }

        deferred0.await()
        deferred1.await()
        deferred2.await()
        deferred3.await()
      }

  @Test
  fun `getting quote should work`() =
      runBlocking<Unit> {
        val deferred0 = async { client.getQuote(symbolId = "2884") }
        val deferred1 = async { client.getQuote(symbolId = "0050") }
        val deferred2 = async { client.getQuote(symbolId = "0050", oddLot = true) }
        val deferred3 = async { client.getQuote(symbolId = "046500") }
        val deferred4 = async { client.getQuote(symbolId = "TW50") }

        deferred0.await()
        deferred1.await()
        deferred2.await()
        deferred3.await()
        deferred4.await()
      }

  @Test
  fun `getting dealts should work`() =
      runBlocking<Unit> {
        val deferred0 = async { client.getDealts(symbolId = "0050") }
        val deferred1 = async { client.getDealts(symbolId = "2330", offset = 2, limit = 5) }
        val deferred2 = async { client.getDealts(symbolId = "2330", oddLot = true) }

        withTimeout(3000) { deferred0.await() }.also {
          val info = it.data.info
          assertAll(
              { assertEquals("EQUITY", info.type) },
              { assertEquals("TWSE", info.exchange) },
              { assertEquals("TSE", info.market) },
              { assertEquals("0050", info.symbolId) },
              { assertEquals("TW", info.countryCode) },
              { assertEquals("Asia/Taipei", info.timeZone) })

          val dealts = it.data.dealts
          assertTrue(dealts.isNotEmpty())
        }

        withTimeout(3000) { deferred1.await() }.also {
          val info = it.data.info
          assertAll({ assertEquals("EQUITY", info.type) }, { assertEquals("2330", info.symbolId) })

          val dealts = it.data.dealts
          assertEquals(5, dealts.size)
        }

        withTimeout(3000) { deferred2.await() }.also {
          val info = it.data.info
          assertAll({ assertEquals("ODDLOT", info.type) }, { assertEquals("2330", info.symbolId) })
        }
      }

  @Test
  fun `getting volumes should work`() =
      runBlocking<Unit> {
        val today = LocalDate.now()

        val deferred0 = async { client.getVolumes(symbolId = "0050") }
        val deferred1 = async { client.getVolumes(symbolId = "2330", oddLot = true) }

        withTimeout(3000) { deferred0.await() }.also {
          val info = it.data.info
          assertAll(
              { assertTrue(abs(today.year - info.date.year) <= 1) },
              { assertEquals("EQUITY", info.type) },
              { assertEquals("TWSE", info.exchange) },
              { assertEquals("TSE", info.market) },
              { assertEquals("0050", info.symbolId) },
              { assertEquals("TW", info.countryCode) },
              { assertEquals("Asia/Taipei", info.timeZone) },
              { assertTrue(abs(today.year - info.lastUpdatedAt!!.year) <= 1) })

          val volumes = it.data.volumes
          assertAll(
              { assertTrue(volumes.isNotEmpty()) },
              { assertTrue(volumes[0].price > 0) },
              { assertTrue(volumes[0].volume > 0) })
        }

        withTimeout(3000) { deferred1.await() }.also {
          val info = it.data.info
          assertAll(
              { assertTrue(abs(today.year - info.date.year) <= 1) },
              { assertEquals("ODDLOT", info.type) },
              { assertEquals("TWSE", info.exchange) },
              { assertEquals("TSE", info.market) },
              { assertEquals("2330", info.symbolId) },
              { assertEquals("TW", info.countryCode) },
              { assertEquals("Asia/Taipei", info.timeZone) },
              { assertTrue(abs(today.year - info.lastUpdatedAt!!.year) <= 1) })

          val volumes = it.data.volumes
          assertAll(
              { assertTrue(volumes.isNotEmpty()) },
              { assertTrue(volumes[0].price > 0) },
              { assertTrue(volumes[0].volume > 0) })
        }
      }

  @Test
  fun `getting candles should work`() =
      runBlocking<Unit> {
        val startDate = LocalDate.of(2022, 4, 18)
        val endDate = LocalDate.of(2022, 4, 22)

        val deferred0 = async {
          client.getCandles(symbolId = "0050", startDate = startDate, endDate = endDate)
        }

        withTimeout(3000) { deferred0.await() }.also {
          assertAll(
              { assertEquals("0050", it.symbolId) },
              { assertEquals("EQUITY", it.type) },
              { assertEquals("TWSE", it.exchange) },
              { assertEquals("TSE", it.market) },
              { assertEquals(5, it.candles.size) })
        }
      }
}
