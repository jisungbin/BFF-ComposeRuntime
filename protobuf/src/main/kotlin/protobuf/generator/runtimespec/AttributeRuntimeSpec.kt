package protobuf.generator.runtimespec

import androidx.collection.IntObjectMap
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.wire.schema.Field
import protobuf.Schemas
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.Names.AttributesCn
import protobuf.generator.Names.BffUiCodegenExceptionCn
import protobuf.generator.Names.checkTypeIfNotNullMn
import protobuf.generator.Names.checkTypeMn
import protobuf.generator.ProtoAliasable
import protobuf.generator.ProtoAttributes
import protobuf.generator.RuntimeGenerator.RUNTIME_GENERATED_PACKAGE
import protobuf.generator.indent
import protobuf.generator.isOptional
import protobuf.generator.snakeToCamel
import protobuf.generator.typeName
import protobuf.generator.unindent

internal object AttributeRuntimeSpec : ProtoAliasable(RUNTIME_GENERATED_PACKAGE) {
  private val attributeTypeMessageResolvers = mutableListOf<FunSpec>()

  internal fun resolverFile(): FileSpec {
    val resolverObject =
      TypeSpec.objectBuilder("AttributeResolver")
        .addModifiers(INTERNAL)
        .addFunction(attributeResolverFun())
        .addFunctions(attributeTypeMessageResolvers)
        .build()

    return FileSpec.builder(RUNTIME_GENERATED_PACKAGE, "AttributeResolver")
      .addFileComment(GENERATED_COMMENT)
      .addAliasedImport(AttributesCn, "UiAttributes")
      .applyProtoAttributesAlias()
      .applyAliased()
      .addType(resolverObject)
      .build()
  }

  private fun attributeResolverFun(): FunSpec {
    val attributeTypes = Schemas.Attributes.oneOf("type")!!.fields

    return FunSpec.builder("resolve")
      .addModifiers(INTERNAL)
      .addParameter("attributes", AttributesCn)
      .returns(LIST.parameterizedBy(ProtoAttributes::class.asClassName()))
      .addStatement("if (attributes === %T) return emptyList()", AttributesCn)
      .addCode("\n")
      .beginControlFlow("return attributes.value.fold(mutableListOf()) { acc, element ->")
      .beginControlFlow("acc += when (val tag = element.index)")
      .apply {
        attributeTypes.forEach { type ->
          addStatement(
            "%L -> %T(%L = %N(element.arguments))",
            type.tag,
            ProtoAttributes::class,
            type.name,
            attributeTypeMessageResolverFun(type).also(attributeTypeMessageResolvers::add),
          )
        }
        addStatement("else -> throw %T(%P)", BffUiCodegenExceptionCn, "\$tag is not a valid attribute tag.")
      }
      .endControlFlow()
      .addStatement("acc")
      .endControlFlow()
      .build()
  }

  private fun attributeTypeMessageResolverFun(typeField: Field): FunSpec {
    val typeMessageFields = Schemas.message(typeField.type!!).declaredFields
    val returns = typeField.type!!.typeName().protoAliased()

    return FunSpec.builder(typeField.name.snakeToCamel())
      .addModifiers(PRIVATE)
      .addParameter("arguments", IntObjectMap::class.asClassName().parameterizedBy(ANY))
      .returns(returns)
      .addCode("return %T(\n", returns)
      .indent()
      .apply {
        typeMessageFields.forEach { parameterField ->
          val fieldTag = parameterField.tag
          val fieldTypeName = parameterField.type!!.typeName()

          if (parameterField.isOptional) {
            addStatement(
              "%L = %M<%T>(arguments[%L]),",
              parameterField.name,
              checkTypeIfNotNullMn,
              fieldTypeName,
              fieldTag,
            )
          } else {
            addStatement(
              "%L = %M<%T>(arguments[%L]),",
              parameterField.name,
              checkTypeMn,
              fieldTypeName,
              fieldTag,
            )
          }
        }
      }
      .unindent()
      .addCode(")")
      .build()
  }
}
