plugins {
  kotlin("jvm")
  kotlin("plugin.compose")
  id("dev.drewhamilton.poko")
  application
  `java-base`
}

application {
  mainClass = "bff.server.MainKt"
}

kotlin {
  compilerOptions {
    optIn.add("kotlin.ExperimentalStdlibApi")
  }
}

dependencies {
  implementation(project(":ui"))

  implementation("com.squareup.okio:okio:3.12.0")
  implementation("com.squareup.moshi:moshi:1.15.2")
  implementation("com.squareup.wire:wire-moshi-adapter:5.3.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}
