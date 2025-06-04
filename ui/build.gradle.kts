plugins {
  kotlin("jvm")
  kotlin("plugin.compose")
  id("dev.drewhamilton.poko")
}

kotlin {
  explicitApi()
}

dependencies {
  api(project(":protobuf"))
  api("com.squareup.wire:wire-runtime:5.3.1")

  implementation("androidx.collection:collection:1.5.0")
  implementation("androidx.compose.runtime:runtime:1.8.2")
}
