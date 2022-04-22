plugins {
  `kotlin-dsl`
  id("com.diffplug.spotless") version "6.4.1"
}

repositories { gradlePluginPortal() }

dependencies {
  val ktVersion = "1.6.21"
  implementation(kotlin("gradle-plugin", version = ktVersion))
  implementation(kotlin("serialization", version = ktVersion))

  implementation("com.diffplug.spotless:spotless-plugin-gradle:6.4.1")
  implementation("com.palantir.gradle.gitversion:gradle-git-version:0.15.0")
}

spotless {
  kotlinGradle {
    target("**/*.gradle.kts")
    ktfmt()
  }
}
