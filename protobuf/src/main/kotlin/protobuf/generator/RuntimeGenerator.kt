package protobuf.generator

import protobuf.generator.runtimespec.ActionRuntimeSpec
import protobuf.generator.runtimespec.AttributeRuntimeSpec
import protobuf.generator.runtimespec.UiNodeRuntimeSpec

internal object RuntimeGenerator {
  internal const val RUNTIME_GENERATED_PACKAGE = "bff.ui.runtime"

  internal fun generateAttributeResolver() {
    val generated = AttributeRuntimeSpec.resolverFile().writeTo(generatedDir)
    println("Generated ${generated.nameWithoutExtension} in ${generated.path}")
  }

  internal fun generateActionResolver() {
    val generated = ActionRuntimeSpec.resolveFile().writeTo(generatedDir)
    println("Generated ${generated.nameWithoutExtension} in ${generated.path}")
  }

  internal fun generateModelBuilder() {
    UiNodeRuntimeSpec.modelBuilderFiles().forEach { file ->
      val generated = file.writeTo(generatedDir)
      println("Generated ${generated.nameWithoutExtension} in ${generated.path}")
    }
  }
}
