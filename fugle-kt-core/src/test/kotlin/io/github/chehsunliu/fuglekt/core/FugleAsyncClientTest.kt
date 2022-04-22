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
  fun `getting candles should work`() = runBlocking {
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
  fun `getting candles in gzip should work`() = runBlocking {
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
        { assertEquals("TSE", response.market) })
  }
}
