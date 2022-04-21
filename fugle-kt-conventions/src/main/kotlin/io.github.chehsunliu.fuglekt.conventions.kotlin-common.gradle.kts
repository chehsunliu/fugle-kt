plugins {
  kotlin("jvm")
  id("com.diffplug.spotless")
  id("com.palantir.git-version")
}

group = "io.github.chehsunliu.fuglekt"

val gitVersion: groovy.lang.Closure<String> by extra

version = gitVersion()

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
