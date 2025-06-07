package protobuf.generator.schemaspec

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.tag
import com.squareup.wire.schema.EnumConstant
import com.squareup.wire.schema.EnumType
import com.squareup.wire.schema.Field
import protobuf.Schemas
import protobuf.generator.AliasMode
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.Names.AttributeCn
import protobuf.generator.Names.AttributesCn
import protobuf.generator.Names.UI_PACKAGE_NAME
import protobuf.generator.ProtoAliasable
import protobuf.generator.SchemaGenerator.SCHEMA_GENERATED_PACKAGE
import protobuf.generator.addNullSafeStatement
import protobuf.generator.applyIf
import protobuf.generator.enumValidatorCode
import protobuf.generator.isOptional
import protobuf.generator.schemaspec.MessageProtoType.Companion.protoType
import protobuf.generator.snakeToCamel
import protobuf.generator.typeName

private typealias MessageTag = Int

internal object AttributeSchemaSpec : ProtoAliasable(SCHEMA_GENERATED_PACKAGE, AliasMode.TypeAlias) {
  private val attributeThenMn = MemberName(UI_PACKAGE_NAME, "then", isExtension = true)
  private val mutableIntObjectMapOfMn = MemberName("androidx.collection", "mutableIntObjectMapOf")

  internal fun attributesFile(): FileSpec {
    val functions = Schemas.Attributes.oneOf("type")!!.fields.map(::attributeFun)

    return FileSpec.builder(SCHEMA_GENERATED_PACKAGE, "attributes")
      .addFileComment(GENERATED_COMMENT)
      .applyAliased()
      .addFunctions(functions)
      .build()
  }

  private fun attributeFun(attributeType: Field): FunSpec {
    val enumParameters = mutableListOf<Pair<ParameterSpec, EnumConstant>>()
    val attributeParameters =
      Schemas.message(attributeType.type!!).declaredFields
        .map { field ->
          ParameterSpec.builder(
            field.name.snakeToCamel(),
            field.type!!.typeName().protoAliased().copy(nullable = field.isOptional),
          )
            .applyIf(field.isOptional) { defaultValue("null") }
            .tag<MessageTag>(field.tag)
            .tag(MessageProtoType(field.type!!))
            .build()
        }
        .onEach { parameter ->
          val enumType = Schemas.type(parameter.protoType()) as? EnumType ?: return@onEach
          val unspecified = enumType.constant(0) ?: return@onEach
          enumParameters.add(parameter to unspecified)
        }
        .sortedBy { it.type.isNullable }

    return FunSpec.builder(attributeType.name.snakeToCamel())
      .receiver(AttributesCn)
      .returns(AttributesCn)
      .addParameters(attributeParameters)
      .applyIf(enumParameters.isNotEmpty()) {
        val tempFun = build()
        enumParameters.forEach { (parameter, unspecified) ->
          addCode("%L\n", enumValidatorCode(tempFun, parameter, unspecified))
        }
        addCode("\n")
      }
      .addStatement("val arguments = %M<%T>()", mutableIntObjectMapOfMn, ANY)
      .addCode(
        buildCodeBlock {
          attributeParameters.forEach { argument ->
            addNullSafeStatement(argument.name, argument.type.isNullable) {
              CodeBlock.of("arguments[%L] = %N", argument.tag<MessageTag>()!!, argument)
            }
          }
        },
      )
      .addCode("\n")
      .addStatement("return this %M %T(%L, arguments)", attributeThenMn, AttributeCn, attributeType.tag)
      .build()
  }
}
