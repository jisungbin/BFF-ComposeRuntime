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
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.MessageType
import protobuf.Schemas
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.Names.BffUiCodegenExceptionCn
import protobuf.generator.Names.HELPER_PACKAGE_NAME
import protobuf.generator.Names.ProtobufNodeCn
import protobuf.generator.Names.UI_PACKAGE_NAME
import protobuf.generator.Names.UiScopeCn
import protobuf.generator.Names.checkChildrenScopeMn
import protobuf.generator.Names.checkScopeMn
import protobuf.generator.Names.checkTypeIfNotNullMn
import protobuf.generator.Names.checkTypeMn
import protobuf.generator.Names.childOfScopeMn
import protobuf.generator.Names.childOfScopeOrNullMn
import protobuf.generator.Names.childrenOfScopeMn
import protobuf.generator.Names.componentBaseMn
import protobuf.generator.RuntimeGenerator.RUNTIME_GENERATED_PACKAGE
import protobuf.generator.UiKind
import protobuf.generator.indent
import protobuf.generator.isOptional
import protobuf.generator.schemaspec.UiNodeSchemaSpec.WellKnownFieldNames
import protobuf.generator.snakeToCamel
import protobuf.generator.typeName
import protobuf.generator.unindent
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
  private val ResponseTypeCn = ClassName(UI_PACKAGE_NAME, "ResponseType")
  private val ActionResolverCn = ClassName(HELPER_PACKAGE_NAME, "ActionResolver")

  private val protoFieldMn = MemberName(UI_PACKAGE_NAME, "protoField", isExtension = true)

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
          addCode("%T -> {\n", ResponseTypeCn.nestedClass(kind))
          indent()
          addStatement("%M<%T>(root)", checkChildrenScopeMn, UiScopeCn.nestedClass(kind))
          addStatement(
            "%T(%L = root.children.map(::build%L))",
            Response::class,
            kind.replaceFirstChar(Char::lowercaseChar) + 's',
            kind,
          )
          unindent()
          addCode("}\n")
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
      .addStatement("%M<%T>(node)", checkChildrenScopeMn, UiScopeCn.nestedClass(contentTypeName))
      .addStatement("val %L = node.children.map(::%L)", contentName, contentBuilderName)
      .apply { regularFields.forEach { addStatement("%L", it.getterStatement) } }
      .addCode("\n")
      .addStatement("val scope = %M<%T>(node)", checkScopeMn, UiScopeCn.nestedClass(kind.name))
      .addStatement("%L", screenOrSectionTypeGetterCode(kind))
      .addCode("\n")
      .addCode("return %T(\n", returns)
      .indent()
      .addStatement("id = node.id,")
      .addStatement("attributes = AttributeResolver.resolve(node.attributes),")
      .addStatement("actions = %T.resolve(node.actions),", ActionResolverCn)
      .addStatement("type = type,")
      .addStatement("%L = %L,", contentName, contentName)
      .apply { regularFields.forEach { addStatement("%L = %L,", it.fieldName, it.getterName) } }
      .unindent()
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
      .addStatement("%M<%T>(node)", checkChildrenScopeMn, UiScopeCn.nestedClass("ChildWidgetOrComponent"))
      .addStatement("val scope = %M<%T>(node)", checkScopeMn, UiScopeCn.nestedClass("Widget"))
      .apply { regularFields.forEach { addStatement("%L", it.getterStatement) } }
      .addCode("\n")
      .beginControlFlow("return when (val contentTag = scope.contentType)")
      .apply {
        contentFields.forEach { field ->
          val (resolver, resolverOrigin) = uiFieldResolverFun("Widget", Schemas.message(field.type!!))
          if (resolverOrigin == Origin.Generated) widgetContentResolvers.add(resolver)

          addCode("%L -> %T(\n", field.tag, Widget::class)
          indent()
          addStatement("id = node.id,")
          addStatement("attributes = AttributeResolver.resolve(node.attributes),")
          addStatement("actions = %T.resolve(node.actions),", ActionResolverCn)
          regularFields.forEach { addStatement("%L = %L,", it.fieldName, it.getterName) }
          addStatement("%L = %M(node),", field.name, ResolverCns.WidgetContent.member(resolver.name))
          unindent()
          addCode(")\n")
        }
        addStatement("else -> throw %T(%P)", BffUiCodegenExceptionCn, "\$contentTag is not a valid Widget's content tag.")
      }
      .endControlFlow()
      .build()
  }

  private fun uiFieldResolverFun(
    containerSimpleName: String,
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

    val currentSimpleName = message.type.simpleName.run { if (kindName != this) removeSuffix(kindName) else this }
    val resolverName = containerSimpleName.replaceFirstChar(Char::lowercaseChar) + currentSimpleName

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
        .returns(message.type.typeName())
        .apply { (regularFields + uiFields).forEach { addStatement("%L", it.getterStatement) } }
        .addCode("\n")
        .addCode("return %T(\n", message.type.typeName())
        .indent()
        .apply {
          when (kindName) {
            "ChildWidget" -> {
              addStatement("id = node.id,")
              addStatement("attributes = AttributeResolver.resolve(node.attributes),")
              addStatement("actions = %T.resolve(node.actions),", ActionResolverCn)
            }
            "Component" -> addStatement("base = node.%M(),", componentBaseMn)
          }
          regularFields.forEach { addStatement("%L = %L,", it.fieldName, it.getterName) }
          uiFields.forEach { uiField ->
            val field = uiField.field
            val fieldType = field.type!!
            val isChildWidget = fieldType.simpleName.endsWith("ChildWidget")

            val (resolver, resolverOrigin) =
              uiFieldResolverFun(currentSimpleName, Schemas.message(fieldType), allowWidgetContent = false)
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
        }
        .unindent()
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
        if (field.isOptional) checkTypeIfNotNullMn else checkTypeMn,
        field.type!!.typeName(),
        protoFieldMn,
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
          field.isRepeated -> childrenOfScopeMn
          field.isOptional -> childOfScopeOrNullMn
          else -> childOfScopeMn // required field
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
