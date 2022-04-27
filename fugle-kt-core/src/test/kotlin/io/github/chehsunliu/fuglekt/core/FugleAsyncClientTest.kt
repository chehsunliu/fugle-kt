package io.github.chehsunliu.fuglekt.core

import io.github.chehsunliu.fuglekt.core.test.TestIOUtil
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class FugleAsyncClientTest {
  private val server = MockWebServer()

  @BeforeEach
  fun setUp() {
    server.start()
  }

  @AfterEach
  fun tearDown() {
    server.shutdown()
  }

  private fun createClient(): FugleAsyncClient =
      FugleAsyncClient.create(url = "http://${server.hostName}:${server.port}", token = "xxx")

  @Test
  fun `getting dealts deserialization should work`() = runBlocking {
    val body = TestIOUtil.resourceToBytes("/marketdata-test/get-dealts_0050_response.json.gz")
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Encoding", "gzip")
            .setBody(Buffer().readFrom(body.inputStream())))

    val client = createClient()
    val response = withTimeout(5000) { client.getDealts(symbolId = "0050") }
    assertEquals("0.3.0", response.apiVersion)

    val info = response.data.info
    assertAll(
        { assertEquals(LocalDate.of(2022, 4, 27), info.date) },
        { assertEquals("EQUITY", info.type) },
        { assertEquals("TWSE", info.exchange) },
        { assertEquals("TSE", info.market) },
        { assertEquals("0050", info.symbolId) },
        { assertEquals("TW", info.countryCode) },
        { assertEquals("Asia/Taipei", info.timeZone) })

    val dealts = response.data.dealts
    assertAll(
        { assertEquals(50, dealts.size) },
        { assertEquals("2022-04-27T11:25:58.821+08:00", dealts[0].at.toString()) },
        { assertEquals(126.7, dealts[0].bid) },
        { assertEquals(126.8, dealts[0].ask) },
        { assertEquals(126.7, dealts[0].price) },
        { assertEquals(1L, dealts[0].volume) },
        { assertEquals(6321031L, dealts[0].serial) })
  }

  @Test
  fun `getting volumes deserialization should work`() = runBlocking {
    val body = TestIOUtil.resourceToBytes("/marketdata-test/get-volumes_2330_response.json.gz")
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Encoding", "gzip")
            .setBody(Buffer().readFrom(body.inputStream())))

    val client = createClient()
    val response = withTimeout(5000) { client.getVolumes(symbolId = "2330") }
    assertEquals("0.3.0", response.apiVersion)

    val info = response.data.info
    assertAll(
        { assertEquals(LocalDate.of(2022, 4, 26), info.date) },
        { assertEquals("EQUITY", info.type) },
        { assertEquals("TWSE", info.exchange) },
        { assertEquals("TSE", info.market) },
        { assertEquals("2330", info.symbolId) },
        { assertEquals("TW", info.countryCode) },
        { assertEquals("Asia/Taipei", info.timeZone) },
        { assertEquals(1650858297L, info.lastUpdatedAt.toEpochSecond()) })

    val volumes = response.data.volumes
    assertAll(
        { assertEquals(7, volumes.size) },
        { assertEquals(52, volumes[0].volume) },
        { assertEquals(551.0, volumes[0].price) })
  }

  @Test
  fun `getting candles deserialization not in gzip encoding should work`() = runBlocking {
    val body = TestIOUtil.gzipResourceToBytes("/marketdata-test/get-candles_2330_response.json.gz")
    server.enqueue(
        MockResponse().setResponseCode(200).setBody(Buffer().readFrom(body.inputStream())))

    val client = createClient()
    val response =
        withTimeout(5000) {
          client.getCandles(
              symbolId = "2330",
              startDate = LocalDate.of(2022, 4, 1),
              endDate = LocalDate.of(2022, 4, 22))
        }

    assertAll(
        { assertEquals("2330", response.symbolId) },
        { assertEquals("EQUITY", response.type) },
        { assertEquals("TWSE", response.exchange) },
        { assertEquals("TSE", response.market) })
  }

  @Test
  fun `getting candles deserialization should work`() = runBlocking {
    val body = TestIOUtil.resourceToBytes("/marketdata-test/get-candles_2330_response.json.gz")
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Encoding", "gzip")
            .setBody(Buffer().readFrom(body.inputStream())))

    val client = createClient()
    val response =
        withTimeout(5000) {
          client.getCandles(
              symbolId = "2330",
              startDate = LocalDate.of(2022, 4, 1),
              endDate = LocalDate.of(2022, 4, 22))
        }

    assertAll(
        { assertEquals("2330", response.symbolId) },
        { assertEquals("EQUITY", response.type) },
        { assertEquals("TWSE", response.exchange) },
        { assertEquals("TSE", response.market) },
        { assertEquals(6, response.candles.size) })

    val candle = response.candles[0]
    assertAll(
        { assertEquals(LocalDate.of(2022, 4, 22), candle.date) },
        { assertEquals(35_567_672, candle.volume) },
        { assertEquals(558.0, candle.open) },
        { assertEquals(558.0, candle.close) },
        { assertEquals(557.0, candle.low) },
        { assertEquals(559.0, candle.high) })
  }
}
