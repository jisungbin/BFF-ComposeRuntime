package protobuf.generator

import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DOUBLE
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.LONG
import com.squareup.kotlinpoet.MAP
import com.squareup.kotlinpoet.NOTHING
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.UNIT
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Field.Label
import com.squareup.wire.schema.ProtoType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

internal val Field.isOptional: Boolean
  inline get() = label == Label.OPTIONAL

internal fun FunSpec.Builder.indent() = addCode("⇥")
internal fun FunSpec.Builder.unindent() = addCode("⇤")

internal inline fun CodeBlock.Builder.addNullSafeStatement(
  name: String,
  nullable: Boolean,
  statement: () -> CodeBlock,
): CodeBlock.Builder =
  apply {
    if (!nullable) return addStatement("%L", statement())
    addStatement("if (%L != null)♢%L", name, statement())
  }

internal fun ProtoType.typeName(): TypeName {
  val knownType = knownTypeName()
  if (knownType != null) return knownType

  return ClassName.bestGuess(toString())
}

internal fun TypeName.className(): ClassName? =
  when (this) {
    is ParameterizedTypeName -> rawType
    is ClassName -> this
    else -> null
  }

// https://github.com/square/wire/blob/a1823b3b46ed5b61f154baa2db0d8e643a792778/wire-kotlin-generator/src/main/java/com/squareup/wire/kotlin/KotlinGenerator.kt#L3104
private fun ProtoType.knownTypeName(): TypeName? =
  when (this) {
    ProtoType.BOOL -> BOOLEAN
    ProtoType.DOUBLE -> DOUBLE
    ProtoType.FLOAT -> FLOAT
    ProtoType.FIXED32 -> INT
    ProtoType.FIXED64 -> LONG
    ProtoType.INT32 -> INT
    ProtoType.INT64 -> LONG
    ProtoType.SFIXED32 -> INT
    ProtoType.SFIXED64 -> LONG
    ProtoType.SINT32 -> INT
    ProtoType.SINT64 -> LONG
    ProtoType.STRING -> STRING
    ProtoType.UINT32 -> INT
    ProtoType.UINT64 -> LONG
    ProtoType.ANY -> ClassName("com.squareup.wire", "AnyMessage")
    ProtoType.DURATION -> ClassName("com.squareup.wire", "Duration")
    ProtoType.TIMESTAMP -> ClassName("com.squareup.wire", "Instant")
    ProtoType.EMPTY -> UNIT
    ProtoType.STRUCT_MAP -> MAP.parameterizedBy(STRING, STAR).copy(nullable = true)
    ProtoType.STRUCT_NULL -> NOTHING.copy(nullable = true)
    ProtoType.STRUCT_LIST -> LIST.parameterizedBy(STAR).copy(nullable = true)
    else -> null
  }

@OptIn(ExperimentalContracts::class)
internal inline fun <T> T.applyIf(condition: Boolean, body: T.() -> Unit): T {
  contract { callsInPlace(body, InvocationKind.AT_MOST_ONCE) }
  return apply { if (condition) body() }
}

internal fun String.snakeToPascal(): String =
  split('_').joinToString("") { it.lowercase().replaceFirstChar(Char::uppercaseChar) }

internal fun String.snakeToCamel(): String = snakeToPascal().replaceFirstChar(Char::lowercaseChar)
