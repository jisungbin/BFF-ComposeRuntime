package protobuf.generator

import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.STRING
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Field.Label
import com.squareup.wire.schema.ProtoType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

internal val Field.isOptional: Boolean
  inline get() = label == Label.OPTIONAL

internal inline fun CodeBlock.Builder.addNullSafeStatement(
  name: String,
  nullable: Boolean,
  statement: () -> CodeBlock,
): CodeBlock.Builder =
  apply {
    if (!nullable) return addStatement("%L", statement())
    addStatement("if (%N != null)♢%L", name, statement())
  }

internal fun ProtoType.className(): ClassName {
  val primitiveClassName = primitiveClassName()
  if (primitiveClassName != null) return primitiveClassName

  return ClassName.bestGuess(toString())
}

// https://github.com/square/wire/blob/a1823b3b46ed5b61f154baa2db0d8e643a792778/wire-kotlin-generator/src/main/java/com/squareup/wire/kotlin/KotlinGenerator.kt#L3104
internal fun ProtoType.primitiveClassName(): ClassName? =
  when (this) {
    ProtoType.BOOL -> BOOLEAN
    ProtoType.FLOAT -> FLOAT
    ProtoType.STRING -> STRING
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
