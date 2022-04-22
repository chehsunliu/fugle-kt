package io.github.chehsunliu.fuglekt.core.test

import java.util.zip.GZIPInputStream

object TestIOUtil {
  fun resourceToBytes(classpath: String): ByteArray {
    val stream = TestIOUtil::class.java.getResourceAsStream(classpath)!!
    return stream.readBytes()
  }

  fun gzipResourceToBytes(classpath: String): ByteArray {
    val stream = TestIOUtil::class.java.getResourceAsStream(classpath)!!
    return GZIPInputStream(stream).use { it.readBytes() }
  }
}
