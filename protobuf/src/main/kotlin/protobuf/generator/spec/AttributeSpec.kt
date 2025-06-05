package protobuf.generator.spec

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.tag
import com.squareup.wire.schema.Field
import protobuf.Schemas
import protobuf.generator.addNullSafeStatement
import protobuf.generator.applyIf
import protobuf.generator.className
import protobuf.generator.isOptional
import protobuf.generator.snakeToCamel

private typealias MessageTag = Int

internal object AttributeSpec {
  private val AttributeCn = ClassName("bff.ui.attribute", "Attribute")
  private val AttributeThenMn = MemberName("bff.ui.attribute", "then", isExtension = true)
  internal val AttributesCn = ClassName("bff.ui.attribute", "Attributes")
  private val MutableIntObjectMapOfMn = MemberName("androidx.collection", "mutableIntObjectMapOf")

  internal fun attribute(field: Field): FunSpec {
    val attributeParameters =
      Schemas.message(field.type!!).declaredFields
        .map { field ->
          ParameterSpec.builder(
            field.name.snakeToCamel(),
            field.type!!.className().copy(nullable = field.isOptional),
          )
            .applyIf(field.isOptional) { defaultValue("null") }
            .tag<MessageTag>(field.tag)
            .build()
        }
        .sortedBy { it.type.isNullable }

    return FunSpec.builder(field.name)
      .receiver(AttributesCn)
      .returns(AttributesCn)
      .addParameters(attributeParameters)
      .addStatement("val arguments = %M<%T>()", MutableIntObjectMapOfMn, ANY)
      .addCode(
        buildCodeBlock {
          attributeParameters.forEach { argument ->
            addNullSafeStatement(argument.name, argument.type.isNullable) {
              CodeBlock.of("arguments[%L] = %N", argument.tag<MessageTag>()!!, argument)
            }
          }
        },
      )
      .addStatement("return this %M %T(%L, arguments)", AttributeThenMn, AttributeCn, field.tag)
      .build()
  }
}