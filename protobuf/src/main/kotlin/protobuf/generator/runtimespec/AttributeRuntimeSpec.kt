package protobuf.generator.runtimespec

import androidx.collection.IntObjectMap
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.wire.schema.Field
import protobuf.Schemas
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.Names.AttributesCn
import protobuf.generator.Names.BffUiCodegenExceptionCn
import protobuf.generator.ProtoAliasable
import protobuf.generator.RuntimeGenerator.RUNTIME_GENERATED_PACKAGE
import protobuf.generator.className
import protobuf.generator.isOptional
import protobuf.generator.snakeToCamel
import protobuf.source.attributes.Attributes

internal object AttributeRuntimeSpec : ProtoAliasable(RUNTIME_GENERATED_PACKAGE) {
  private val attributeTypeMessageResolvers = mutableListOf<FunSpec>()

  internal val resolveFunctionMn = ClassName(RUNTIME_GENERATED_PACKAGE, "AttributeResolver").member("resolve")

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
      .returns(LIST.parameterizedBy(Attributes::class.asClassName()))
      .addStatement("if (attributes === %T) return emptyList()", AttributesCn)
      .addCode("\n")
      .beginControlFlow("return attributes.value.fold(mutableListOf()) { acc, element ->")
      .addCode("acc += when (val tag = element.index) {\n")
      .addCode(
        buildCodeBlock {
          indent()
          attributeTypes.forEach { type ->
            addStatement(
              "%L -> %T(%L = %N(element.arguments))",
              type.tag,
              Attributes::class,
              type.name,
              attributeTypeMessageResolverFun(type).also(attributeTypeMessageResolvers::add),
            )
          }
          addStatement("else -> throw %T(%P)", BffUiCodegenExceptionCn, "\$tag is not a valid attribute tag.")
          unindent()
        },
      )
      .addCode("}\n")
      .addCode("acc\n")
      .endControlFlow()
      .build()
  }

  private fun attributeTypeMessageResolverFun(typeField: Field): FunSpec {
    val typeMessageFields = Schemas.message(typeField.type!!).declaredFields
    val returns = typeField.type!!.className().protoAliased()

    return FunSpec.builder(typeField.name.snakeToCamel())
      .addModifiers(PRIVATE)
      .addParameter("arguments", IntObjectMap::class.asClassName().parameterizedBy(ANY))
      .returns(returns)
      .addCode("return %T(\n", returns)
      .addCode(
        buildCodeBlock {
          indent()
          typeMessageFields.forEach { parameterField ->
            val fieldTag = parameterField.tag
            val fieldTypeCn = parameterField.type!!.className()

            if (parameterField.isOptional) {
              addStatement(
                "%N = arguments[%L] as? %T,",
                parameterField.name,
                fieldTag,
                fieldTypeCn,
              )
            } else {
              addStatement(
                "%N = arguments[%L] as? %T ?: throw %T(%P),",
                parameterField.name,
                fieldTag,
                fieldTypeCn,
                BffUiCodegenExceptionCn,
                "Expected type is ${fieldTypeCn.canonicalName}, but was \n" +
                  "\${arguments[$fieldTag]?.javaClass?.canonicalName}.",
              )
            }
          }
          unindent()
        },
      )
      .addCode(")")
      .build()
  }
}
