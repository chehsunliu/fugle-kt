plugins {
  id("io.github.chehsunliu.fuglekt.conventions.kotlin-lib")
  id("io.github.chehsunliu.fuglekt.conventions.integration-test")
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")

  implementation("com.squareup.okhttp3:okhttp")
  testImplementation("com.squareup.okhttp3:mockwebserver")
}
