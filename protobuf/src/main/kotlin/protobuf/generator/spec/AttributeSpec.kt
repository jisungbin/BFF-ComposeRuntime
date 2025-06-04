package protobuf.generator.spec

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.tag
import com.squareup.wire.schema.Field
import protobuf.Schema
import protobuf.generator.GeneratedResult
import protobuf.generator.applyIf
import protobuf.generator.className
import protobuf.generator.isOptional
import protobuf.generator.snakeToCamel

internal object AttributeSpec {
  internal const val ATTRIBURE_PACKAGE = "bff.ui.attribute"
  internal val AttributesCn = ClassName(ATTRIBURE_PACKAGE, "Attributes")

  private val AttributeCn = ClassName(ATTRIBURE_PACKAGE, "Attribute")

  internal fun attribute(field: Field): GeneratedResult.Attribute {
    val attributeArguments =
      Schema.message(field.type!!).declaredFields
        .map { field ->
          ParameterSpec.builder(
            field.name.snakeToCamel(),
            field.type!!.className().copy(nullable = field.isOptional),
          )
            .applyIf(field.isOptional) { defaultValue("null") }
            .tag(field.tag)
            .build()
        }
        .sortedBy { it.type.isNullable }
    val attributeFun =
      FunSpec.builder(field.name)
        .receiver(AttributesCn)
        .returns(AttributesCn)
        .addParameters(attributeArguments)
        .addStatement("val arguments = mutableIntObjectMapOf<%T>()", ANY)
        .apply {
          attributeArguments.forEach { argument ->
            addCode("«")
            if (argument.type.isNullable) addCode("if (%N != null)♢", argument)
            addCode("arguments[%L] = %N", argument.tag<Int>()!!, argument)
            addCode("\n»")
          }
        }
        .addStatement("return this then %T(%L, arguments)", AttributeCn, field.tag)
        .build()

    return GeneratedResult.Attribute(attributeFun)
  }
}