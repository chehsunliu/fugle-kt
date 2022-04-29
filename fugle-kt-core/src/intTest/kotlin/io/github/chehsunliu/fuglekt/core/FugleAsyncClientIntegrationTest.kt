package io.github.chehsunliu.fuglekt.core

import java.time.LocalDate
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

private data class TestCase(
    val symbolId: String,
    val hasOddLot: Boolean = false,
    val hasCandles: Boolean = true,
)

internal class FugleAsyncClientIntegrationTest {
  private val client = FugleAsyncClient.create(token = System.getenv("FUGLE_TOKEN"))

  private val startDate = LocalDate.of(2022, 4, 18)
  private val endDate = LocalDate.of(2022, 4, 22)

  private val testCases =
      listOf(
          TestCase(symbolId = "2884", hasOddLot = true),
          TestCase(symbolId = "0050", hasOddLot = true),
          TestCase(symbolId = "046500", hasCandles = false),
          TestCase(symbolId = "TW50", hasCandles = false),
      )

  @Test
  fun `Queries should work`() = runBlocking {
    testCases
        .map {
          val l1 =
              listOf(
                  async { client.getMeta(symbolId = it.symbolId) },
                  async { client.getQuote(symbolId = it.symbolId) },
                  async { client.getDealts(symbolId = it.symbolId) },
                  async { client.getVolumes(symbolId = it.symbolId) },
                  async { client.getChart(symbolId = it.symbolId) },
              )

          val l2 =
              if (it.hasOddLot)
                  listOf(
                      async { client.getMeta(symbolId = it.symbolId, oddLot = true) },
                      async { client.getQuote(symbolId = it.symbolId, oddLot = true) },
                      async { client.getDealts(symbolId = it.symbolId, oddLot = true) },
                      async { client.getVolumes(symbolId = it.symbolId, oddLot = true) },
                      async { client.getChart(symbolId = it.symbolId, oddLot = true) },
                  )
              else emptyList()

          val l3 =
              if (it.hasCandles)
                  listOf(
                      async {
                        client.getCandles(
                            symbolId = it.symbolId, startDate = startDate, endDate = endDate)
                      })
              else emptyList()

          Pair(it, l1 + l2 + l3)
        }
        .forEach { (testCase, deferred) ->
          deferred.forEach { assertDoesNotThrow(testCase.symbolId) { it.await() } }
        }
  }
}
