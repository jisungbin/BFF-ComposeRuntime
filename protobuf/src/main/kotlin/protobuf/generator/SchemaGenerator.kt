package protobuf.generator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
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
  function: FunSpec,
  argument: ParameterSpec,
  unspecifiedValue: EnumConstant,
): CodeBlock {
  val signature = buildString {
    append(function.receiverType?.toString()?.substringAfterLast('.')?.plus('.').orEmpty())
    append(function.name + "(...)")
    append(" 함수의 ")
    append(argument.name)
    append(" 인자")
  }

  return buildCodeBlock {
    add("if (%N == %T.%L)\n", argument, argument.type, unspecifiedValue.name)
    indent()
    add("throw %T(\n", IllegalArgumentException::class)
    withIndent {
      add("%S +\n", "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 ")
      withIndent { add("%S,\n", "Protobuf field를 optional로 만들고 null을 제공하세요. ($signature)") }
    }
    add(")")
    unindent()
  }
}
