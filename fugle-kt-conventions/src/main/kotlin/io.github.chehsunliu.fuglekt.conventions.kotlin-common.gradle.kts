plugins {
  kotlin("jvm")
  id("com.diffplug.spotless")
}

group = "io.github.chehsunliu.fuglekt"

version = "0.1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

spotless {
  kotlin { ktfmt() }
  kotlinGradle { ktfmt() }
}

tasks.test { useJUnitPlatform() }
