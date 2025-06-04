plugins {
  kotlin("jvm") version "2.1.21" apply false
  kotlin("plugin.compose") version "2.1.21" apply false
  id("dev.drewhamilton.poko") version "0.18.7" apply false
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
