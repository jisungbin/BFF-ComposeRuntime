package protobuf.generator

import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Field.Label
import com.squareup.wire.schema.ProtoType
import java.io.File
import jdk.jfr.internal.TypeLibrary.addTypes
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import protobuf.Schema
import protobuf.generator.spec.AttributeSpec
import protobuf.generator.spec.AttributeSpec.ATTRIBURE_PACKAGE
import protobuf.generator.spec.UiNodeSpec

internal object UiGenerator {
  internal val GENERATED_DIR = projectDir.resolve("ui/src/main/kotlin")
  internal const val UI_GENERATED_PACKAGE = "bff.ui.generated"

  internal fun generateAttributes() {
    val generatedAttributes = FileSpec.builder(ATTRIBURE_PACKAGE, "GeneratedAttributes")

    val typeFields = Schema.Attributes.oneOf("type")!!.fields
    typeFields.forEach { field ->
      generatedAttributes.addFunction(AttributeSpec.attribute(field).attributeFunction)
    }

    val generated =
      generatedAttributes
        .addImport("androidx.collection", "mutableIntObjectMapOf")
        .addFileComment(GENERATED_COMMENT)
        .build()
        .writeTo(GENERATED_DIR)
    println("Generated attributes in ${generated.path}")
  }

  internal fun generateUiNodes() {
    val componentScopeInterfaces = mutableListOf<TypeSpec>()
    val componentScopeProviders = mutableListOf<TypeSpec>()

    fun generateScreenOrSection(kind: UiKind): File {
      val messageType = when (kind) {
        UiKind.Screen -> Schema.Screen
        UiKind.Section -> Schema.Section
        UiKind.Widget -> Schema.Widget
        else -> throw IllegalArgumentException("Unsupported UiKind: $kind")
      }

      val generated = UiNodeSpec.uiNode(messageType)
      val scopeInterfaces = generated.scopeInterfaces
      val scopeProviders = generated.scopeProviders

      componentScopeInterfaces.addAll(scopeInterfaces.filter { it.name!!.endsWith("ComponentScope") })
      componentScopeProviders.addAll(scopeProviders.filter { it.name!!.endsWith("ComponentScopeProvider") })

      return FileSpec.builder(UI_GENERATED_PACKAGE, "${kind.name}Uis")
        .addTypes(scopeInterfaces.filterNot { it.name!!.endsWith("ComponentScope") })
        .addTypes(scopeProviders.filterNot { it.name!!.endsWith("ComponentScopeProvider") })
        .addFunctions(generated.nodeFunctions)
        .addFileComment(GENERATED_COMMENT)
        .build()
        .writeTo(GENERATED_DIR)
    }

    println("Generated Screen nodes in ${generateScreenOrSection(UiKind.Screen).path}")
    println("Generated Section nodes in ${generateScreenOrSection(UiKind.Section).path}")
    println("Generated Widget nodes in ${generateScreenOrSection(UiKind.Widget).path}")
  }
}

internal val Field.isOptional: Boolean
  inline get() = label == Label.OPTIONAL

internal fun ProtoType.className(): ClassName {
  val primitiveClassName = primitiveClassName()
  if (primitiveClassName != null) return primitiveClassName

  return ClassName.bestGuess(toString())
}

internal fun ProtoType.primitiveClassName(): ClassName? =
  when (this) {
    ProtoType.BOOL -> BOOLEAN
    ProtoType.FLOAT -> FLOAT
    ProtoType.STRING -> STRING
    else -> null
  }

// https://github.com/JetBrains/kotlin/blob/a6853f7f3c9dd72cbb1706a19a25e2bad722f08e/core/util.runtime/src/org/jetbrains/kotlin/utils/addToStdlib.kt#L271-L272
@OptIn(ExperimentalContracts::class)
internal inline fun <T, R : T> R.applyIf(condition: Boolean, body: R.() -> T): T {
  contract { callsInPlace(body, InvocationKind.AT_MOST_ONCE)  }
  return if (condition) body() else this
}

internal fun String.snakeToPascal(): String =
  split('_').joinToString("") { it.lowercase().replaceFirstChar(Char::uppercaseChar) }

internal fun String.snakeToCamel(): String = snakeToPascal().replaceFirstChar(Char::lowercaseChar)
