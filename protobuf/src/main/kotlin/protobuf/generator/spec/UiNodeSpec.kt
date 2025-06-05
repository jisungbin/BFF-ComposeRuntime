package protobuf.generator.spec

import androidx.compose.runtime.Composable
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.ABSTRACT
import com.squareup.kotlinpoet.KModifier.DATA
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.OVERRIDE
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
import protobuf.Schemas
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.UiGenerator.UI_GENERATED_PACKAGE
import protobuf.generator.addNullSafeStatement
import protobuf.generator.applyIf
import protobuf.generator.className
import protobuf.generator.isOptional
import protobuf.generator.snakeToCamel
import protobuf.generator.snakeToPascal
import protobuf.generator.spec.AttributeSpec.AttributesCn
import protobuf.generator.spec.MessageProtoTag.Companion.protoTag
import protobuf.generator.spec.MessageProtoType.Companion.protoType
import protobuf.generator.spec.UiScopeMarker.Companion.provider

@JvmInline private value class MessageProtoTag(val tag: Int) {
  companion object {
    fun ParameterSpec.protoTag(): Int = tag<MessageProtoTag>()!!.tag
  }
}

@JvmInline private value class MessageProtoType(val type: ProtoType) {
  companion object {
    fun ParameterSpec.protoType(): ProtoType = tag<MessageProtoType>()!!.type
  }
}

private sealed class UiNode(val message: MessageType) {
  val name get() = this::class.simpleName!!

  open val contentParamName: String? get() = null
  open val contentUiScopeName: String? get() = null

  class Screen(message: MessageType) : UiNode(message) {
    override val contentParamName: String get() = "sections"
    override val contentUiScopeName: String get() = "SectionScope"
  }

  class Section(message: MessageType) : UiNode(message) {
    override val contentParamName: String get() = "widgets"
    override val contentUiScopeName: String get() = "WidgetScope"
  }

  class Widget(message: MessageType) : UiNode(message)

  class ChildWidgetOrComponent(
    message: MessageType,
    val fieldTag: Int,
    val uiScopeCn: ClassName,
  ) : UiNode(message)
}

private data class UiType(
  val name: String,
  val tag: Int,
  val message: MessageType? = null,
)

private data class UiScopeMarker(
  val scopeInterface: TypeSpec,
  val scopeProvider: TypeSpec,
) {
  companion object {
    fun ClassName.provider(): ClassName = ClassName(packageName, simpleName + "Provider")
  }
}

private data class NodeParameters(
  val regularParameters: List<ParameterSpec>,
  val uiParameters: List<ParameterSpec>,
)

internal object UiNodeSpec {
  private val ActionsCn = ClassName("bff.ui.action", "Actions")
  private val UiScopeCn = ClassName("bff.ui", "UiScope")
  private val UiScopeMarkerCn = ClassName("bff.ui", "UiScopeMarker")

  private val ProtobufNodeCn = ClassName("bff.ui", "ProtobufNode")
  private val ProtobufApplierCn = ClassName("bff.ui", "ProtobufApplier")
  private val ProtobufFieldTagCn = ClassName("bff.ui", "ProtobufFieldTag")

  private val wellKnownFieldNames =
    listOf(
      "id",
      "attributes",
      "actions",
      "base",
      "type",
      "sections",
      "widgets",
      "content",
    )

  internal fun uiNodeFiles(): List<FileSpec> {
    fun MessageType.enumUiTypes(): List<UiType> =
      (nestedTypes.first { it is EnumType && it.name == "Type" } as EnumType)
        .constants
        .mapNotNull { constant ->
          if (constant.tag == 0) return@mapNotNull null
          UiType(constant.name, constant.tag)
        }

    val screenUiNode = UiNode.Screen(Schemas.Screen)
    val screenUiTypes = Schemas.Screen.enumUiTypes()

    val sectionUiNode = UiNode.Section(Schemas.Section)
    val sectionUiTypes = Schemas.Section.enumUiTypes()

    val widgetUiNode = UiNode.Widget(Schemas.Widget)
    val widgetUiTypes =
      Schemas.Widget.oneOf("content")!!.fields
        .filter { it.tag != 0 }
        .map { UiType(it.name, it.tag, Schemas.message(it.type!!)) }


    val screenUiFunctions = screenUiTypes.map { uiFunction(screenUiNode, it) }
    val sectionUiFunctions = sectionUiTypes.map { uiFunction(sectionUiNode, it) }
    val widgetUiFunctions = widgetUiTypes.map { uiFunction(widgetUiNode, it) }


    val protobufUiScope =
      uiScopeMarker(ClassName(UI_GENERATED_PACKAGE, "ProtobufUiScope"), screenUiFunctions)

    val screenContentUiScopeCn =
      screenUiFunctions.first()
        .parameters
        .last { it.name == screenUiNode.contentParamName }
        .uiScopeCn()
    val screenUiScopeMarker = uiScopeMarker(screenContentUiScopeCn, sectionUiFunctions)

    val sectionContentUiScopeCn =
      sectionUiFunctions.first()
        .parameters
        .last { it.name == sectionUiNode.contentParamName }
        .uiScopeCn()
    val sectionUiScopeMarker = uiScopeMarker(sectionContentUiScopeCn, widgetUiFunctions)


    val widgetUiScopeMarkers = mutableListOf<List<UiScopeMarker>>()
    for (widgetUiFunction in widgetUiFunctions) {
      val childWidgetOrComponentNodes =
        widgetUiFunction.parameters
          .mapNotNull { it.tag<UiNode.ChildWidgetOrComponent>() }
          .toMutableList()
      val uiScopeMarkers = mutableListOf<UiScopeMarker>()

      while (childWidgetOrComponentNodes.isNotEmpty()) {
        val node = childWidgetOrComponentNodes.removeFirst()
        val uiFunction = uiFunction(node, uiType = null)
        val newNodeCandidates = uiFunction.parameters.mapNotNull { it.tag<UiNode.ChildWidgetOrComponent>() }

        uiScopeMarkers.add(uiScopeMarker(node.uiScopeCn, listOf(uiFunction)))
        childWidgetOrComponentNodes.addAll(newNodeCandidates)
      }

      widgetUiScopeMarkers.add(uiScopeMarkers)
    }


    val screenFile =
      FileSpec.builder(UI_GENERATED_PACKAGE, "ScreenUis")
        .addFileComment(GENERATED_COMMENT)
        .addType(protobufUiScope.scopeInterface)
        .addType(protobufUiScope.scopeProvider)
        .build()

    val sectionFile =
      FileSpec.builder(UI_GENERATED_PACKAGE, "SectionUis")
        .addFileComment(GENERATED_COMMENT)
        .addType(screenUiScopeMarker.scopeInterface)
        .addType(screenUiScopeMarker.scopeProvider)
        .build()

    val widgetFile =
      FileSpec.builder(UI_GENERATED_PACKAGE, "WidgetScope")
        .addFileComment(GENERATED_COMMENT)
        .addType(sectionUiScopeMarker.scopeInterface)
        .addType(sectionUiScopeMarker.scopeProvider)
        .build()

    val childWidgetOrComponentFiles = Array(widgetUiFunctions.size) { index ->
      FileSpec.builder(UI_GENERATED_PACKAGE, widgetUiFunctions[index].name)
        .addFileComment(GENERATED_COMMENT)
        .addTypes(widgetUiScopeMarkers[index].map { it.scopeInterface })
        .addTypes(widgetUiScopeMarkers[index].map { it.scopeProvider })
        .build()
    }

    return listOf(
      screenFile,
      sectionFile,
      widgetFile,
      *childWidgetOrComponentFiles,
    )
  }

  private fun uiFunction(uiNode: UiNode, uiType: UiType?): FunSpec {
    val (regularParameters, uiParameters) = uiNode.parameters(uiType)
    val enumParameters = regularParameters.mapNotNull { parameter ->
      val enumType = Schemas.type(parameter.protoType()) as? EnumType ?: return@mapNotNull null
      val unspecified = enumType.constant(0) ?: return@mapNotNull null
      parameter to unspecified
    }

    val functionName: String
    val fieldTag: Int
    val currentCompositeKeyHashMn = MemberName("androidx.compose.runtime", "currentCompositeKeyHash")

    when (uiNode) {
      is UiNode.Screen, is UiNode.Section, is UiNode.Widget -> {
        requireNotNull(uiType) { "UiType must be provided for Screen, Section and Widget nodes." }
        functionName = "${uiType.name.removePrefix("TYPE").snakeToPascal()}${uiNode.name}"
        fieldTag = uiType.tag
      }
      is UiNode.ChildWidgetOrComponent -> {
        functionName = uiNode.message.type.simpleName
        fieldTag = uiNode.fieldTag
      }
    }

    return FunSpec.builder(functionName)
      .addAnnotation(Composable::class)
      // Parameters
      .addParameters(defaultParameters()) // TODO(dx) default argument 정렬에 포함시킬까?
      .addParameters(regularParameters.sortedByDescending { it.defaultValue == null })
      .addParameters(
        uiParameters.sortedWith(
          compareBy<ParameterSpec> { it.defaultValue == null }
            .thenBy { it.protoType().simpleName.endsWith("ChildWidget") },
        ),
      )
      .applyIf(uiNode.contentParamName != null) {
        addParameter(
          uiNode.contentParamName!!,
          LambdaTypeName.get(
            receiver = ClassName(UI_GENERATED_PACKAGE, uiNode.contentUiScopeName!!),
            returnType = UNIT,
          )
            .copy(annotations = listOf(AnnotationSpec.builder(Composable::class).build())),
        )
      }
      // Body
      .applyIf(enumParameters.isNotEmpty()) {
        enumParameters.forEach { (parameter, unspecified) ->
          addStatement("%L\n", enumValidator(parameter, unspecified))
        }
      }
      .addStatement("val currentCompositeKeyHash = %M", currentCompositeKeyHashMn)
      .addCode("\n")
      .addCode(composeNode(uiNode, uiScope(uiNode, fieldTag), regularParameters, uiParameters))
      .build()
  }

  private fun UiNode.parameters(uiType: UiType?): NodeParameters {
    val uiNode = this
    val regularParameters = mutableListOf<ParameterSpec>()
    val uiParameters = mutableListOf<ParameterSpec>()

    val declaredFields = when (uiNode) {
      is UiNode.Widget -> {
        requireNotNull(uiType) { "UiType must be provided for Widget nodes." }
        requireNotNull(uiType.message) { "UiType must have a 'message' for Widget nodes." }
        uiType.message.declaredFields
      }
      else -> uiNode.message.declaredFields
    }

    declaredFields
      .filter { it.name !in wellKnownFieldNames }
      .forEach { field ->
        val protoType = field.type!!
        val isUiField = protoType.simpleName.run { endsWith("ChildWidget") || endsWith("Component") }

        val uiScopeBaseName =
          when (uiNode) {
            is UiNode.Widget -> uiType!!.message!!.type.simpleName.removeSuffix("WidgetContent")
            is UiNode.ChildWidgetOrComponent -> uiNode.uiScopeCn.simpleName.removeSuffix("Scope")
            else -> uiNode.message.type.simpleName.removeSuffix("Component")
          }
            .plus(field.name.snakeToPascal())
            .plus("Scope")

        val uiScopeCn = ClassName(UI_GENERATED_PACKAGE, uiScopeBaseName)
        val childWidgetOrComponentNode = when {
          isUiField -> UiNode.ChildWidgetOrComponent(Schemas.message(protoType), field.tag, uiScopeCn)
          else -> null
        }
        val parameter =
          ParameterSpec.builder(
            field.name.snakeToCamel(),
            when {
              isUiField -> {
                LambdaTypeName.get(receiver = uiScopeCn, returnType = UNIT)
                  .copy(annotations = listOf(AnnotationSpec.builder(Composable::class).build()))
              }
              else -> field.type!!.className().copy(nullable = field.isOptional)
            },
          )
            .applyIf(field.isOptional) { defaultValue(if (isUiField) "{}" else "null") }
            .tag(MessageProtoTag(field.tag))
            .tag(MessageProtoType(protoType))
            .tag(childWidgetOrComponentNode)
            .build()

        if (isUiField) uiParameters.add(parameter) else regularParameters.add(parameter)
      }

    return NodeParameters(regularParameters, uiParameters)
  }

  private fun composeNode(
    uiNode: UiNode,
    uiScope: CodeBlock,
    regularParameters: List<ParameterSpec>,
    uiParameters: List<ParameterSpec>,
  ): CodeBlock {
    val defaultInitArguments =
      listOf(
        "this",
        "attributes",
        "actions",
        uiScope,
        "currentCompositeKeyHash",
      )
    val composeNodeMn = MemberName("androidx.compose.runtime", "ComposeNode")

    return buildCodeBlock {
      add("%M<%T, %T>(\n", composeNodeMn, ProtobufNodeCn, ProtobufApplierCn)
      indent()
      add("factory = %T.CONSTRUCTOR,\n", ProtobufNodeCn)
      add("update = {\n")
      withIndent {
        add("%T.INIT(\n", ProtobufNodeCn)
        indent()
        defaultInitArguments.forEach { add("%L,\n", it) }
        unindent()
        add(")\n")
        if (regularParameters.isNotEmpty()) {
          beginControlFlow("init")
          regularParameters.forEach { parameter ->
            addNullSafeStatement(parameter.name, parameter.type.isNullable) {
              CodeBlock.of("data[%T(%L)] = %N", ProtobufFieldTagCn, parameter.protoTag(), parameter)
            }
          }
          endControlFlow()
        }
      }
      add("},\n")
      unindent()
      add(") {\n")
      indent()
      if (uiNode.contentParamName != null && uiNode.contentUiScopeName != null) {
        addStatement("%LProvider.%L()", uiNode.contentUiScopeName, uiNode.contentParamName)
      }
      uiParameters.forEach { parameter ->
        addStatement("%T.%N()", parameter.uiScopeCn().provider(), parameter)
      }
      unindent()
      add("}")
    }
  }

  private fun uiScope(uiNode: UiNode, tag: Int): CodeBlock =
    CodeBlock.of("%M(%L)", UiScopeCn.member(uiNode.name), tag)

  private fun uiScopeMarker(name: ClassName, functions: List<FunSpec>): UiScopeMarker =
    UiScopeMarker(scopeInterface(name, functions), scopeProvider(name, functions))

  private fun ParameterSpec.uiScopeCn(): ClassName =
    (type as LambdaTypeName).receiver as ClassName

  private fun scopeInterface(name: ClassName, functions: List<FunSpec>): TypeSpec =
    TypeSpec.interfaceBuilder(name)
      .addModifiers(SEALED)
      .addAnnotation(UiScopeMarkerCn)
      .addFunctions(functions.map { it.toBuilder().addModifiers(ABSTRACT).clearBody().build() })
      .build()

  private fun scopeProvider(scopeInterface: ClassName, functions: List<FunSpec>): TypeSpec =
    TypeSpec.objectBuilder(scopeInterface.provider())
      .addModifiers(INTERNAL, DATA)
      .addSuperinterface(scopeInterface)
      .addFunctions(
        functions.map {
          it.toBuilder()
            .addModifiers(OVERRIDE)
            .apply {
              parameters.onEachIndexed { index, parameter ->
                parameters[index] = parameter.toBuilder().defaultValue(null).build()
              }
            }
            .build()
        },
      )
      .build()

  private fun defaultParameters(): List<ParameterSpec> =
    listOf(
      ParameterSpec.builder("attributes", AttributesCn)
        .defaultValue("%T", AttributesCn)
        .build(),
      ParameterSpec.builder("actions", ActionsCn)
        .defaultValue("%T", ActionsCn)
        .build(),
    )

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
