plugins {
  java
  `maven-publish`
  signing
}

java {
  withJavadocJar()
  withSourcesJar()
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
      pom {
        name.set("fugle-kt")
        description.set("A client for Fugle Realtime API")
        url.set("https://github.com/chehsunliu/fugle-kt")

        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }

        developers {
          developer {
            id.set("chehsunliu")
            name.set("Che-Hsun Liu")
            email.set("chehsunliu@gmail.com")
          }
        }

        scm {
          connection.set("scm:git:git://github.com/chehsunliu/fugle-kt.git")
          developerConnection.set("scm:git:ssh://github.com/chehsunliu/fugle-kt.git")
          url.set("https://github.com/chehsunliu/fugle-kt")
        }
      }
    }
  }

  repositories {
    maven {
      val snapshotUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
      val stagingUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

      url = if (version.toString().endsWith("SNAPSHOT")) snapshotUrl else stagingUrl
      credentials {
        username = System.getenv("OSSRH_USERNAME")
        password = System.getenv("OSSRH_PASSWORD")
      }
    }
  }
}

signing {
  val signingKeyId = System.getenv("SIGNING_KEY_ID")
  val signingKey = System.getenv("SIGNING_KEY")
  val signingPassword = System.getenv("SIGNING_PASSWORD")
  useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)

  sign(publishing.publications["maven"])
}
