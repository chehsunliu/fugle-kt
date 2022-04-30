package io.github.chehsunliu.fuglekt.example

import io.github.chehsunliu.fuglekt.core.FugleAsyncClient
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  FugleAsyncClient.create(token = System.getenv("FUGLE_TOKEN")).use { client ->
    val response = client.getMeta(symbolId = "2330")
    println(response)
  }
}
