package protobuf.generator

import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.STRING
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Field.Label
import com.squareup.wire.schema.ProtoType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import protobuf.Schemas
import protobuf.generator.spec.AttributeSpec
import protobuf.generator.spec.UiNodeSpec

internal object UiGenerator {
  internal val GENERATED_DIR = projectDir.resolve("ui/src/main/kotlin")
  internal const val UI_GENERATED_PACKAGE = "bff.ui.schema"

  internal fun generateAttributes() {
    val attributesFile = FileSpec.builder(UI_GENERATED_PACKAGE, "attributes")

    val typeFields = Schemas.Attributes.oneOf("type")!!.fields
    typeFields.forEach { field ->
      attributesFile.addFunction(AttributeSpec.attribute(field))
    }

    val generated =
      attributesFile
        .addFileComment(GENERATED_COMMENT)
        .build()
        .writeTo(GENERATED_DIR)
    println("Generated attributes in ${generated.path}")
  }

  internal fun generateUiNodes() {
    UiNodeSpec.uiNodeFiles().forEach { file ->
      val generated = file.writeTo(GENERATED_DIR)
      println("Generated ${generated.nameWithoutExtension} nodes in ${generated.path}")
    }
  }
}

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

@Suppress("NOTHING_TO_INLINE") // Syntactic sugar.
internal inline fun <T> unsafeLazy(noinline initializer: () -> T): Lazy<T> =
  lazy(LazyThreadSafetyMode.NONE, initializer)

internal fun String.snakeToPascal(): String =
  split('_').joinToString("") { it.lowercase().replaceFirstChar(Char::uppercaseChar) }

internal fun String.snakeToCamel(): String = snakeToPascal().replaceFirstChar(Char::lowercaseChar)

