plugins {
  kotlin("jvm")
}

kotlin {
  explicitApi()
}

dependencies {
  implementation("com.squareup.okio:okio:3.12.0")
  implementation("com.squareup:kotlinpoet:2.2.0")

  implementation("androidx.collection:collection:1.5.0")
  implementation("androidx.compose.runtime:runtime:1.8.2")

  implementation("com.squareup.wire:wire-runtime:5.3.1")
  implementation("com.squareup.wire:wire-compiler:5.3.1")
  implementation("com.squareup.wire:wire-schema:5.3.1")
  implementation("com.squareup.wire:wire-kotlin-generator:5.3.1")
}
