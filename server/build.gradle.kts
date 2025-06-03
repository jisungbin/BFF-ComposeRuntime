plugins {
  kotlin("jvm")
  kotlin("plugin.compose")
  id("dev.drewhamilton.poko") version "0.18.7"
  application
  `java-base`
}

application {
  mainClass = "bffui.composeruntime.poc.MainKt"
}

dependencies {
  implementation(project(":protobuf"))
  implementation("com.squareup.okio:okio:3.12.0")
  implementation("com.squareup.moshi:moshi:1.15.2")
  implementation("com.squareup.wire:wire-moshi-adapter:5.3.1")

  implementation("androidx.collection:collection:1.5.0")
  implementation("androidx.compose.runtime:runtime:1.8.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}
