package protobuf.generator.runtimespec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import protobuf.generator.GENERATED_COMMENT
import protobuf.generator.Names.ActionsCn
import protobuf.generator.Names.BffUiCodegenExceptionCn
import protobuf.generator.RuntimeGenerator.RUNTIME_GENERATED_PACKAGE
import protobuf.source.action.Action

internal object ActionRuntimeSpec {
  internal val resolveFunctionMn = ClassName(RUNTIME_GENERATED_PACKAGE, "ActionResolver").member("resolve")

  internal fun resolveFile(): FileSpec {
    val resolverObject =
      TypeSpec.objectBuilder("ActionResolver")
        .addModifiers(INTERNAL)
        .addFunction(resolveFun())
        .build()

    return FileSpec.builder(RUNTIME_GENERATED_PACKAGE, "ActionResolver")
      .addFileComment(GENERATED_COMMENT)
      .addType(resolverObject)
      .build()
  }

  private fun resolveFun(): FunSpec =
    FunSpec.builder("resolve")
      .addModifiers(INTERNAL)
      .addParameter("actions", ActionsCn)
      .returns(LIST.parameterizedBy(Action::class.asClassName()))
      .addStatement("if (actions === %T) return emptyList()", ActionsCn)
      .addCode("\n")
      .beginControlFlow("return actions.value.fold(mutableListOf()) { acc, element ->")
      .addStatement("val id = element.id ?: throw %T(%S)", BffUiCodegenExceptionCn, "The Action's id has not been initialised.")
      .addStatement("acc.add(%T(id, element.type, element.triggerCondition, element.uriScheme))", Action::class)
      .addStatement("acc")
      .endControlFlow()
      .build()
}