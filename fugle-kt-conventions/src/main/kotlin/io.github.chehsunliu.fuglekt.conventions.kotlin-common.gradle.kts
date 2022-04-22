plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  id("com.diffplug.spotless")
  id("com.palantir.git-version")
}

group = "io.github.chehsunliu.fuglekt"

val gitVersion: groovy.lang.Closure<String> by extra

version = gitVersion()

repositories { mavenCentral() }

dependencies {
  constraints { implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2") }

  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.1"))
  implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))

  testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

spotless {
  kotlin { ktfmt() }
  kotlinGradle { ktfmt() }
}

tasks.test { useJUnitPlatform() }
