plugins { kotlin("jvm") }

sourceSets {
  create("intTest") {
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
  }
}

val intTestImplementation: Configuration by configurations.getting {
  extendsFrom(configurations.implementation.get())
}

configurations["intTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
  intTestImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

task<Test>("integrationTest") {
  description = "Runs integration tests."
  group = "verification"

  testClassesDirs = sourceSets["intTest"].output.classesDirs
  classpath = sourceSets["intTest"].runtimeClasspath
  shouldRunAfter("test")

  useJUnitPlatform()
}
