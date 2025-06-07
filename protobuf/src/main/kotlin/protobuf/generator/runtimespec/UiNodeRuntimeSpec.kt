package protobuf.generator.runtimespec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.withIndent
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.MessageType
import protobuf.Schemas
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.Names.BffUiCodegenExceptionCn
import protobuf.generator.Names.ProtobufNodeCn
import protobuf.generator.Names.UiScopeCn
import protobuf.generator.RuntimeGenerator.RUNTIME_GENERATED_PACKAGE
import protobuf.generator.UiKind
import protobuf.generator.className
import protobuf.generator.isOptional
import protobuf.generator.schemaspec.UiNodeSchemaSpec.WellKnownFieldNames
import protobuf.generator.snakeToCamel
import protobuf.source.response.Response
import protobuf.source.screen.Screen
import protobuf.source.section.Section
import protobuf.source.widget.Widget

private class FieldSpec(
  val field: Field,
  val getterStatement: CodeBlock,
) {
  val fieldName get() = field.name
  val getterName get() = fieldName.snakeToCamel()
}

private enum class Origin {
  Recycled,
  Generated,
}

internal object UiNodeRuntimeSpec {
  private val ResponseTypeCn = ClassName("bff.ui", "ResponseType")
  private val ProtoFieldMn = MemberName("bff.ui", "protoField", isExtension = true)

  private object HelperMns {
    val childOfScope = MemberName("bff.ui.helper", "childOfScope", isExtension = true)
    val childOfScopeOrNull = MemberName("bff.ui.helper", "childOfScopeOrNull", isExtension = true)
    val childrenOfScope = MemberName("bff.ui.helper", "childrenOfScope", isExtension = true)
    val componentBase = MemberName("bff.ui.helper", "componentBase", isExtension = true)

    val checkType = MemberName("bff.ui.helper", "checkType")
    val checkTypeIfNotNull = MemberName("bff.ui.helper", "checkTypeIfNotNull")
    val checkScope = MemberName("bff.ui.helper", "checkScope")
    val checkChildrenScope = MemberName("bff.ui.helper", "checkChildrenScope")
  }

  private object ResolverCns {
    val WidgetContent = ClassName(RUNTIME_GENERATED_PACKAGE, "WidgetContentResolver")
    val ChildWidget = ClassName(RUNTIME_GENERATED_PACKAGE, "ChildWidgetResolver")
    val Component = ClassName(RUNTIME_GENERATED_PACKAGE, "ComponentResolver")

    val entries = listOf(WidgetContent, ChildWidget, Component)
  }

  private val widgetContentResolvers = mutableListOf<FunSpec>()
  private val childWidgetResolvers = mutableListOf<FunSpec>()
  private val componentResolvers = mutableListOf<FunSpec>()

  internal fun modelBuilderFiles(): List<FileSpec> {
    val modelBuilder =
      TypeSpec.objectBuilder("ModelBuilder")
        .addModifiers(INTERNAL)
        .addFunction(responseFun())
        .addFunction(buildScreenOrSectionFun(UiKind.Screen))
        .addFunction(buildScreenOrSectionFun(UiKind.Section))
        .addFunction(buildWidgetFun())
        .build()
    val modelBuilderFile =
      FileSpec.builder(RUNTIME_GENERATED_PACKAGE, modelBuilder.name!!)
        .addFileComment(GENERATED_COMMENT)
        .addType(modelBuilder)
        .build()

    val resolverFiles =
      listOf(widgetContentResolvers, childWidgetResolvers, componentResolvers).mapIndexed { index, resolverFuns ->
        val resolverContainer =
          TypeSpec.objectBuilder(ResolverCns.entries[index].simpleName)
            .addModifiers(INTERNAL)
            .addFunctions(resolverFuns)
            .build()

        FileSpec.builder(RUNTIME_GENERATED_PACKAGE, resolverContainer.name!!)
          .addFileComment(GENERATED_COMMENT)
          .addType(resolverContainer)
          .build()
      }

    return listOf(modelBuilderFile, *resolverFiles.toTypedArray())
  }

  private fun responseFun(): FunSpec =
    FunSpec.builder("response")
      .addModifiers(INTERNAL)
      .addParameter("root", ProtobufNodeCn.nestedClass("Root"))
      .returns(Response::class)
      .beginControlFlow("return when (root.type)")
      .apply {
        listOf("Screen", "Widget").forEach { kind ->
          addStatement("%T -> {", ResponseTypeCn.nestedClass(kind))
          addCode("⇥")
          addStatement("%M<%T>(root)", HelperMns.checkChildrenScope, UiScopeCn.nestedClass(kind))
          addStatement(
            "%T(%L = root.children.map(::build%L))",
            Response::class,
            kind.replaceFirstChar(Char::lowercaseChar) + 's',
            kind,
          )
          addCode("⇤")
          addStatement("}")
        }
      }
      .endControlFlow()
      .build()

  private fun buildScreenOrSectionFun(kind: UiKind): FunSpec {
    if (kind != UiKind.Screen && kind != UiKind.Section) {
      throw IllegalArgumentException("Unexpected UiKind: $kind")
    }

    val schema = if (kind == UiKind.Screen) Schemas.Screen else Schemas.Section
    val regularFields = schema.regularFields()

    val builderName = if (kind == UiKind.Screen) "buildScreen" else "buildSection"
    val contentName = if (kind == UiKind.Screen) "sections" else "widgets"
    val contentTypeName = if (kind == UiKind.Screen) "Section" else "Widget"
    val contentBuilderName = if (kind == UiKind.Screen) "buildSection" else "buildWidget"
    val returns = if (kind == UiKind.Screen) Screen::class else Section::class

    return FunSpec.builder(builderName)
      .addModifiers(PRIVATE)
      .addParameter("node", ProtobufNodeCn)
      .returns(returns)
      .addStatement("%M<%T>(node)", HelperMns.checkChildrenScope, UiScopeCn.nestedClass(contentTypeName))
      .addStatement("val %L = node.children.map(::%L)", contentName, contentBuilderName)
      .apply { regularFields.forEach { addStatement("%L", it.getterStatement) } }
      .addCode("\n")
      .addStatement("val scope = %M<%T>(node)", HelperMns.checkScope, UiScopeCn.nestedClass(kind.name))
      .addStatement("%L", screenOrSectionTypeGetterCode(kind))
      .addCode("\n")
      .addCode("return %T(\n", returns)
      .addCode(
        buildCodeBlock {
          indent()
          addStatement("id = node.id,")
          addStatement("attributes = %M(node.attributes),", AttributeRuntimeSpec.resolveFunctionMn)
          addStatement("actions = %M(node.actions),", ActionRuntimeSpec.resolveFunctionMn)
          addStatement("type = type,")
          addStatement("%L = %L,", contentName, contentName)
          regularFields.forEach { addStatement("%L = %L,", it.fieldName, it.getterName) }
          unindent()
        },
      )
      .addCode(")")
      .build()
  }

  private fun buildWidgetFun(): FunSpec {
    val schema = Schemas.Widget
    val regularFields = schema.regularFields()
    val contentFields = schema.oneOf("content")!!.fields

    return FunSpec.builder("buildWidget")
      .addModifiers(PRIVATE)
      .addParameter("node", ProtobufNodeCn)
      .returns(Widget::class)
      .addStatement("%M<%T>(node)", HelperMns.checkChildrenScope, UiScopeCn.nestedClass("ChildWidgetOrComponent"))
      .addStatement("val scope = %M<%T>(node)", HelperMns.checkScope, UiScopeCn.nestedClass("Widget"))
      .apply { regularFields.forEach { addStatement("%L", it.getterStatement) } }
      .addCode("\n")
      .beginControlFlow("return when (val contentTag = scope.contentType)")
      .apply {
        contentFields.forEach { field ->
          val (resolver, resolverOrigin) = widgetContentOrChildWidgetOrComponentResolverFun(Schemas.message(field.type!!))
          if (resolverOrigin == Origin.Generated) widgetContentResolvers.add(resolver)

          addCode(
            buildCodeBlock {
              add("%L -> %T(\n", field.tag, Widget::class)
              withIndent {
                addStatement("id = node.id,")
                addStatement("attributes = %M(node.attributes),", AttributeRuntimeSpec.resolveFunctionMn)
                addStatement("actions = %M(node.actions),", ActionRuntimeSpec.resolveFunctionMn)
                regularFields.forEach { addStatement("%L = %L,", it.fieldName, it.getterName) }
                addStatement("%L = %M(node),", field.name, ResolverCns.WidgetContent.member(resolver.name))
              }
              add(")\n")
            },
          )
        }
        addStatement(
          "else -> throw %T(%P)",
          BffUiCodegenExceptionCn,
          "\$contentTag is not a valid Widget's content tag.",
        )
      }
      .endControlFlow()
      .build()
  }

  private fun widgetContentOrChildWidgetOrComponentResolverFun(
    message: MessageType,
    allowWidgetContent: Boolean = true,
  ): Pair<FunSpec, Origin> {
    val kindName = when {
      message.type.simpleName.endsWith("WidgetContent") -> "WidgetContent"
      message.type.simpleName.endsWith("ChildWidget") -> "ChildWidget"
      message.type.simpleName.endsWith("Component") -> "Component"
      else -> throw IllegalArgumentException("Unexpected message type: ${message.type.simpleName}")
    }
    if (!allowWidgetContent && kindName == "WidgetContent") {
      throw IllegalArgumentException("WidgetContent resolver is not allowed in this context.")
    }

    val resolverName =
      message.type.simpleName.removeSuffix(kindName).replaceFirstChar(Char::lowercaseChar)

    val existedResolver = when (kindName) {
      "WidgetContent" -> widgetContentResolvers
      "ChildWidget" -> childWidgetResolvers
      else -> componentResolvers
    }
      .find { it.name == resolverName }
    if (existedResolver != null) return existedResolver to Origin.Recycled

    val regularFields = message.regularFields()
    val uiFields = message.uiFields()

    val generated =
      FunSpec.builder(resolverName)
        .addModifiers(INTERNAL)
        .addParameter("node", ProtobufNodeCn)
        .returns(message.type.className())
        .apply { (regularFields + uiFields).forEach { addStatement("%L", it.getterStatement) } }
        .addCode("\n")
        .addCode("return %T(\n", message.type.className())
        .addCode(
          buildCodeBlock {
            indent()
            when (kindName) {
              "ChildWidget" -> {
                addStatement("id = node.id,")
                addStatement("attributes = %M(node.attributes),", AttributeRuntimeSpec.resolveFunctionMn)
                addStatement("actions = %M(node.actions),", ActionRuntimeSpec.resolveFunctionMn)
              }
              "Component" -> addStatement("base = node.%M(),", HelperMns.componentBase)
            }
            regularFields.forEach { addStatement("%L = %L,", it.fieldName, it.getterName) }
            uiFields.forEach { uiField ->
              val field = uiField.field
              val fieldType = field.type!!
              val isChildWidget = fieldType.simpleName.endsWith("ChildWidget")

              val (resolver, resolverOrigin) =
                widgetContentOrChildWidgetOrComponentResolverFun(Schemas.message(fieldType), allowWidgetContent = false)
              if (resolverOrigin == Origin.Generated) {
                if (isChildWidget) childWidgetResolvers.add(resolver) else componentResolvers.add(resolver)
              }

              val resolverContainerName = when {
                isChildWidget -> ResolverCns.ChildWidget.simpleName
                else -> ResolverCns.Component.simpleName
              }

              addStatement(
                "%L = %L,",
                field.name,
                when {
                  field.isRepeated -> "${uiField.getterName}.map(${resolverContainerName}::${resolver.name})"
                  field.isOptional -> "${uiField.getterName}?.let($resolverContainerName::${resolver.name})"
                  else -> "$resolverContainerName.${resolver.name}(${uiField.getterName})" // required field
                },
              )
            }
            unindent()
          },
        )
        .addCode(")")
        .build()
    return generated to Origin.Generated
  }

  private fun screenOrSectionTypeGetterCode(kind: UiKind): CodeBlock {
    val typeEnumCn = when (kind) {
      UiKind.Screen -> Screen.Type::class.asClassName()
      UiKind.Section -> Section.Type::class.asClassName()
      else -> throw IllegalArgumentException("Unexpected UiKind: $kind")
    }

    return CodeBlock.of(
      "val type = %T.fromValue(scope.type)\n?: throw %T(%P)",
      typeEnumCn,
      BffUiCodegenExceptionCn,
      "\${scope.type} is not a valid ${typeEnumCn.simpleNames.joinToString(".")} tag.",
    )
  }

  private fun MessageType.regularFields(): List<FieldSpec> {
    fun getterCode(field: Field): CodeBlock =
      CodeBlock.of(
        "val %L = %M<%T>(node.%M(%L, require = %L))",
        field.name.snakeToCamel(),
        if (field.isOptional) HelperMns.checkTypeIfNotNull else HelperMns.checkType,
        field.type!!.className(),
        ProtoFieldMn,
        field.tag,
        !field.isOptional,
      )

    return declaredFields.filter { field ->
      field.name !in WellKnownFieldNames &&
        !field.type!!.simpleName.run { endsWith("ChildWidget") || endsWith("Component") }
    }
      .map { FieldSpec(it, getterCode(it)) }
  }

  private fun MessageType.uiFields(): List<FieldSpec> {
    fun getterCode(field: Field): CodeBlock =
      CodeBlock.of(
        "val %L = node.%M(%T(%L))",
        field.name.snakeToCamel(),
        when {
          field.isRepeated -> HelperMns.childrenOfScope
          field.isOptional -> HelperMns.childOfScopeOrNull
          else -> HelperMns.childOfScope // required field
        },
        UiScopeCn.nestedClass("ChildWidgetOrComponent"),
        field.tag,
      )

    return declaredFields.filter { field ->
      field.name !in WellKnownFieldNames &&
        field.type!!.simpleName.run { endsWith("ChildWidget") || endsWith("Component") }
    }
      .map { FieldSpec(it, getterCode(it)) }
  }
}
