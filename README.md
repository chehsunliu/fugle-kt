# fugle-kt

[![Build](https://github.com/chehsunliu/fugle-kt/actions/workflows/build.yml/badge.svg)](https://github.com/chehsunliu/fugle-kt/actions/workflows/build.yml)
[![Publish](https://github.com/chehsunliu/fugle-kt/actions/workflows/publish.yml/badge.svg)](https://github.com/chehsunliu/fugle-kt/actions/workflows/publish.yml)
[![Maven](https://img.shields.io/maven-central/v/io.github.chehsunliu.fuglekt/fugle-kt-core)](https://search.maven.org/artifact/io.github.chehsunliu.fuglekt/fugle-kt-core)

fugle-kt is a client for Fugle Realtime API.

## Installation

Fetch the dependency via Gradle:

```kotlin
implementation("io.github.chehsunliu.fuglekt:fugle-kt-core:VERSION")

implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.1"))
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
```

## Usage

Get the metadata of TSMC:

```kotlin
import io.github.chehsunliu.fuglekt.core.FugleAsyncClient
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  FugleAsyncClient.create(token = "xxx").use { client ->
    val response = client.getMeta(symbolId = "2330")
    println(response)
  }
}

// GetMetaResponse(apiVersion=0.3.0, data=Data(info=StockInfo(date=2022-04-29, type=EQUITY, exchange=TWSE, market=TSE, symbolId=2330, countryCode=TW, timeZone=Asia/Taipei, lastUpdatedAt=2022-04-29T13:30+08:00), meta=Meta(market=TSE, nameZhTw=台積電, industryZhTw=半導體業, priceReference=531.0, priceHighLimit=584.0, priceLowLimit=478.0, canDayBuySell=true, canDaySellBuy=true, canShortMargin=true, canShortLend=true, tradingUnit=1000, currency=TWD, isTerminated=false, isSuspended=false, typeZhTw=一般股票, abnormal=正常, isUnusuallyRecommended=false, isNewlyCompiled=null)))
```

Here is the `FugleAsyncClient` interface:

```kotlin
interface FugleAsyncClient : Closeable {
  suspend fun getMeta(symbolId: String, oddLot: Boolean? = null): GetMetaResponse
  suspend fun getQuote(symbolId: String, oddLot: Boolean? = null): GetQuoteResponse
  suspend fun getChart(symbolId: String, oddLot: Boolean? = null): GetChartResponse
  suspend fun getDealts(symbolId: String, limit: Int? = null, offset: Int? = null, oddLot: Boolean? = null): GetDealtsResponse
  suspend fun getVolumes(symbolId: String, oddLot: Boolean? = null): GetVolumesResponse
  suspend fun getCandles(symbolId: String, startDate: LocalDate, endDate: LocalDate): GetCandlesResponse
}
```
