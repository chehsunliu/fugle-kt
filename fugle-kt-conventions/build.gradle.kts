plugins {
  `kotlin-dsl`
  id("com.diffplug.spotless") version "6.4.1"
}

repositories { gradlePluginPortal() }

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
  implementation("com.diffplug.spotless:spotless-plugin-gradle:6.4.1")
}

spotless {
  kotlinGradle {
    target("**/*.gradle.kts")
    ktfmt()
  }
}
