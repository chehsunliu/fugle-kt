package io.github.chehsunliu.fuglekt.core

import okhttp3.Response

class FugleKtException(val msg: String, val code: Int) : Exception(msg) {
  companion object {
    fun from(response: Response): FugleKtException {
      return FugleKtException(msg = response.message, code = response.code)
    }
  }
}
