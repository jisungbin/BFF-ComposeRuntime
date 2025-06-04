package protobuf.generator.spec

import androidx.compose.runtime.Composable
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.DATA
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.KModifier.SEALED
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.tag
import com.squareup.kotlinpoet.withIndent
import com.squareup.wire.schema.EnumConstant
import com.squareup.wire.schema.EnumType
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.ProtoType
import protobuf.Schema
import protobuf.generator.GeneratedResult
import protobuf.generator.UiGenerator.UI_GENERATED_PACKAGE
import protobuf.generator.UiKind
import protobuf.generator.applyIf
import protobuf.generator.className
import protobuf.generator.isOptional
import protobuf.generator.snakeToCamel
import protobuf.generator.snakeToPascal
import protobuf.generator.spec.AttributeSpec.AttributesCn

private typealias MessageTag = Int

@JvmInline private value class ComponentScopeInterfaces(val types: Set<TypeSpec>)

private class UiType(
  val name: String,
  val tag: Int,
  val message: MessageType? = null,
)

internal object UiNodeSpec {
  private val UiScopeCn = ClassName("bff.ui", "UiScope")
  private val UiScopeMarkerCn = ClassName("bff.ui", "UiScopeMarker")

  private val ActionsCn = ClassName("bff.ui.action", "Actions")
  private val ProtobufNodeCn = ClassName("bff.ui", "ProtobufNode")
  private val ProtobufApplierCn = ClassName("bff.ui", "ProtobufApplier")

  private val ExtraFieldCn = ClassName("bff.ui.internal", "ExtraField")
  private val WidgetContentFieldCn = ClassName("bff.ui.internal", "WidgetContentField")
  private val WidgetContentComponentTagCn = ClassName("bff.ui.internal", "WidgetContentComponentTag")

  private val ComposeNodeMn = MemberName("androidx.compose.runtime", "ComposeNode")
  private val LocalWidgetContentFieldTagMn = MemberName("bff.ui.internal", "LocalWidgetContentFieldTag")

  private val wellKnownFieldNames =
    listOf(
      "id",
      "attributes",
      "actions",
      "type",
      "sections",
      "widgets",
      "content",
    )

  internal fun uiNode(message: MessageType): GeneratedResult.UiNode {
    val kind = when (message.name) {
      "Screen" -> UiKind.Screen
      "Section" -> UiKind.Section
      "Widget" -> UiKind.Widget
      else -> throw IllegalArgumentException("Unsupported message name for UI node: ${message.name}")
    }
    val types = when (kind) {
      UiKind.Screen, UiKind.Section -> {
        val typeEnum = message.nestedTypes.first { it.name == "Type" } as EnumType
        typeEnum.constants.map { UiType(it.name, it.tag) }
      }
      UiKind.Widget -> {
        val contentFields = message.oneOf("content")!!.fields
        contentFields.map { UiType(it.name, it.tag, Schema.message(it.type!!)) }
      }
      else -> error("Unsupported UiKind: $kind")
    }

    val scopeInterface = if (kind != UiKind.Widget) scopeInterface(kind.name) else null
    val scopeProvider = scopeInterface?.let(::scopeProvider)

    val allScopeInterfaces = mutableSetOf<TypeSpec>().also { if (scopeInterface != null) it.add(scopeInterface) }
    val allScopeProviders = mutableSetOf<TypeSpec>().also { if (scopeProvider != null) it.add(scopeProvider) }

    val nodes = types.mapNotNull { type ->
      if (type.tag == 0) return@mapNotNull null // Skip the unspecified value

      typedUiNode(
        message = message,
        kind = kind,
        typeName = type.name,
        typeTag = type.tag,
        typeMessage = type.message,
        contentScopeInterface = scopeInterface,
        contentScopeProvider = scopeProvider,
      )
        .also { uiNode ->
          val componentScopeInterfaces = uiNode.tag<ComponentScopeInterfaces>()?.types.orEmpty()
          allScopeInterfaces.addAll(componentScopeInterfaces)
          allScopeProviders.addAll(componentScopeInterfaces.map(::scopeProvider))
        }
    }

    return GeneratedResult.UiNode(allScopeInterfaces.toList(), allScopeProviders.toList(), nodes)
  }

  private fun scopeInterface(baseName: String): TypeSpec =
    TypeSpec.interfaceBuilder("${baseName}Scope")
      .addAnnotation(UiScopeMarkerCn)
      .addModifiers(SEALED)
      .build()

  private fun scopeProvider(scopeInterface: TypeSpec): TypeSpec =
    TypeSpec.objectBuilder("${scopeInterface.name!!}Provider")
      .addModifiers(PRIVATE, DATA)
      .addSuperinterface(ClassName(UI_GENERATED_PACKAGE, scopeInterface.name!!))
      .build()

  private fun typedUiNode(
    message: MessageType,
    kind: UiKind,
    typeName: String,
    typeTag: Int,
    typeMessage: MessageType?,
    contentScopeInterface: TypeSpec?,
    contentScopeProvider: TypeSpec?,
  ): FunSpec {
    val contentParamName = when (kind) {
      UiKind.Widget -> null
      else -> UiKind.entries[kind.ordinal + 1].name.lowercase() + 's'
    }
    val widgetContentParameters = when (kind) {
      UiKind.Widget -> widgetContentParameters(typeMessage!!)
      else -> emptyList()
    }
    val widgetContentFieldParameters = widgetContentParameters.filter { it.type is ClassName }
    val widgetContentComponentParameters = widgetContentParameters.filter { it.type is LambdaTypeName }

    val unhandledFields = message.declaredFields.filter { it.name !in wellKnownFieldNames }
    val enumParameters = mutableListOf<ParameterSpec>()
    val extraFieldParameters = unhandledFields.map { extraField ->
      val protoType = extraField.type!!

      ParameterSpec.builder(
        extraField.name.snakeToCamel(),
        protoType.className().copy(nullable = extraField.isOptional),
      )
        .applyIf(extraField.isOptional) { defaultValue("null") }
        .tag(extraField.tag)
        .build()
        .also { parameterSpec ->
          val type = Schema.type(protoType)
          if (type is EnumType) {
            val unspecifiedValue = type.constants.find { it.tag == 0 }
            if (unspecifiedValue != null) {
              enumParameters.add(parameterSpec.toBuilder().tag(unspecifiedValue).build())
            }
          }
        }
    }

    val componentScopeInterfaces = mutableSetOf<TypeSpec>()

    return FunSpec.builder("${typeName.removePrefix("TYPE").snakeToPascal()}${kind.name}")
      .addAnnotation(Composable::class)
      .applyIf(kind != UiKind.Screen) {
        val parentScopeName = when (kind) {
          UiKind.Section -> "ScreenScope"
          UiKind.Widget -> "SectionScope"
          else -> error("Unexpected UiKind: $kind")
        }

        this
          .receiver(ClassName(UI_GENERATED_PACKAGE, parentScopeName))
          .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
              .addMember("%S", "UnusedReceiverParameter")
              .build(),
          )
      }
      .addParameter(
        ParameterSpec.builder("attributes", AttributesCn)
          .defaultValue("%T", AttributesCn)
          .build(),
      )
      .addParameter(
        ParameterSpec.builder("actions", ActionsCn)
          .defaultValue("%T", ActionsCn)
          .build(),
      )
      .addParameters(extraFieldParameters)
      .apply {
        when {
          kind != UiKind.Widget -> {
            addParameter(
              contentParamName!!,
              LambdaTypeName.get(
                receiver = ClassName(UI_GENERATED_PACKAGE, contentScopeInterface!!.name!!),
                returnType = UNIT,
              )
                .copy(annotations = listOf(AnnotationSpec.builder(Composable::class).build())),
            )
          }
          else -> widgetContentParameters.forEach(::addParameter)
        }
      }
      .apply {
        val (lambdas, nonLambdas) = parameters.partition { it.type is LambdaTypeName }
        parameters.clear()
        parameters.addAll(
          nonLambdas
            .sortedByDescending { it.defaultValue == null }
            .sortedByDescending { it.name == "attributes" || it.name == "actions" },
        )
        parameters.addAll(
          lambdas.sortedWith(
            compareBy<ParameterSpec> { it.defaultValue == null }
              .thenBy {
                val receiverType = (it.type as LambdaTypeName).receiver as ClassName
                receiverType.simpleName.contains("ChildWidget")
              },
          ),
        )
      }
      .applyIf(enumParameters.isNotEmpty()) {
        enumParameters.forEach { parameter ->
          addCode(
            buildCodeBlock {
              add("«")
              if (parameter.type.isNullable) add("if (%N != null)\n", parameter)
              add(enumValidator(parameter, parameter.tag<EnumConstant>()!!))
              add("\n»")
            },
          )
        }
        addCode("\n")
      }
      .addStatement(
        "val currentCompositeKeyHash = %M",
        MemberName("androidx.compose.runtime", "currentCompositeKeyHash"),
      )
      .addCode("\n")
      .addCode(
        buildCodeBlock {
          add("%M<%T, %T>(\n", ComposeNodeMn, ProtobufNodeCn, ProtobufApplierCn)
          indent()
          add("factory = %T.CONSTRUCTOR,\n", ProtobufNodeCn)
          add("update = {\n")
          withIndent {
            addStatement(
              "%T.INIT(\nthis,\nattributes,\nactions,\n%L,\ncurrentCompositeKeyHash,",
              ProtobufNodeCn,
              uiScope(kind, typeTag),
            )
            add(")\n")
            add("init {\n")
            withIndent {
              extraFieldParameters.forEach { parameter ->
                add("«")
                if (parameter.type.isNullable) add("if (%N != null)♢", parameter)
                add("data[%T(%L)] = %N", ExtraFieldCn, parameter.tag<MessageTag>()!!, parameter)
                add("\n»")
              }
              widgetContentFieldParameters.forEach { parameter ->
                add("«")
                if (parameter.type.isNullable) add("if (%N != null)♢", parameter)
                add("data[%T(%L)] = %N", WidgetContentFieldCn, parameter.tag<MessageTag>()!!, parameter)
                add("\n»")
              }
            }
            add("}\n")
          }
          add("},\n")
          unindent()
          add(") {\n")
          indent()
          if (kind != UiKind.Widget) {
            add("%N.%L()\n", contentScopeProvider!!, contentParamName!!)
          }
          widgetContentComponentParameters.forEach { parameter ->
            val scopeName = parameter.tag<ProtoType>()!!.scopeName(typeMessage!!.type)
            val scopeInterface = scopeInterface(scopeName)
            componentScopeInterfaces.add(scopeInterface)

            add(
              "%M(%M provides %L) {\n",
              MemberName("androidx.compose.runtime", "CompositionLocalProvider"),
              LocalWidgetContentFieldTagMn,
              parameter.tag<MessageTag>()!!,
            )
            withIndent { addStatement("%N.%N()", scopeProvider(scopeInterface), parameter) }
            add("}\n")
          }
          unindent()
          add("}")
        },
      )
      .tag(ComponentScopeInterfaces(componentScopeInterfaces))
      .build()
  }

  private fun widgetContentParameters(contentMessage: MessageType): List<ParameterSpec> =
    contentMessage.declaredFields.map { field ->
      val protoType = field.type!!
      val isComponent = protoType.simpleName.endsWith("Component")
      val isChildWidget = protoType.simpleName.endsWith("ChildWidget")

      ParameterSpec.builder(
        field.name.snakeToCamel(),
        when {
          isComponent || isChildWidget -> {
            val scopeName = protoType.scopeName(contentMessage.type)

            LambdaTypeName.get(
              receiver = ClassName(UI_GENERATED_PACKAGE, scopeInterface(scopeName).name!!),
              returnType = UNIT,
            )
              .copy(annotations = listOf(AnnotationSpec.builder(Composable::class).build()))
          }
          else -> protoType.className().copy(nullable = field.isOptional)
        },
      )
        .applyIf(field.isOptional) { defaultValue(if (isComponent || isChildWidget) "{}" else "null") }
        .tag(field.tag)
        .tag(protoType)
        .build()
    }

  private fun ProtoType.scopeName(widgetContentType: ProtoType? = null): String =
    when {
      simpleName.endsWith("Component") -> simpleName
      widgetContentType != null -> widgetContentType.simpleName.removeSuffix("WidgetContent") + simpleName
      else -> error("Cannot determine scope name for $this")
    }

  private fun uiScope(kind: UiKind, tag: Int): CodeBlock =
    CodeBlock.of("%M(%L)", UiScopeCn.member(kind.name), tag)

  private fun enumValidator(
    argument: ParameterSpec,
    unspecifiedValue: EnumConstant,
  ): CodeBlock =
    CodeBlock.of(
      "if (%N == %T.%L)\nerror(%S)",
      argument,
      argument.type,
      unspecifiedValue.name,
      "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우\n" +
        "Protobuf field를 optional로 만들고 null을 제공하세요. (${argument.name})",
    )
}
