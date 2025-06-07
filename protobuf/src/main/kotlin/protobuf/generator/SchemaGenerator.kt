package protobuf.generator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.wire.schema.EnumConstant
import protobuf.generator.schemaspec.AttributeSchemaSpec
import protobuf.generator.schemaspec.UiNodeSchemaSpec

internal object SchemaGenerator {
  internal const val SCHEMA_GENERATED_PACKAGE = "bff.ui.schema"

  internal fun generateAttributes() {
    val generated = AttributeSchemaSpec.attributesFile().writeTo(generatedDir)
    println("Generated attributes in ${generated.path}")
  }

  internal fun generateUiNodes() {
    UiNodeSchemaSpec.uiNodeFiles().forEach { file ->
      val generated = file.writeTo(generatedDir)
      println("Generated ${generated.nameWithoutExtension} nodes in ${generated.path}")
    }
  }
}

internal fun enumValidatorCode(
  argument: ParameterSpec,
  unspecifiedValue: EnumConstant,
): CodeBlock =
  CodeBlock.of(
    "if (%N == %T.%L)\nthrow IllegalArgumentException(%S)",
    argument,
    argument.type,
    unspecifiedValue.name,
    "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우\n" +
      "Protobuf field를 optional로 만들고 null을 제공하세요. (${argument.name})",
  )
