plugins {
  kotlin("jvm") version "1.6.21"
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(project(":fugle-kt-core"))

  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.1"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}
