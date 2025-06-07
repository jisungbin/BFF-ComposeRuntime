// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import bff.ui.BffUiCodegenException
import bff.ui.ProtobufNode
import bff.ui.ResponseType
import bff.ui.UiScope
import bff.ui.helper.checkChildrenScope
import bff.ui.helper.checkScope
import bff.ui.helper.checkType
import bff.ui.helper.checkTypeIfNotNull
import bff.ui.protoField
import bff.ui.runtime.WidgetContentResolver.cellDivider
import bff.ui.runtime.WidgetContentResolver.searchHospitalA
import kotlin.Boolean
import kotlin.String
import protobuf.source.response.Response
import protobuf.source.screen.Screen
import protobuf.source.section.Section
import protobuf.source.widget.Widget
import bff.ui.runtime.ActionResolver.resolve as actionResolverResolve
import bff.ui.runtime.AttributeResolver.resolve as attributeResolverResolve

internal object ModelBuilder {
  internal fun response(root: ProtobufNode.Root): Response = when (root.type) {
    ResponseType.Screen -> {
      checkChildrenScope<UiScope.Screen>(root)
      Response(screens = root.children.map(::buildScreen))
    }
    ResponseType.Widget -> {
      checkChildrenScope<UiScope.Widget>(root)
      Response(widgets = root.children.map(::buildWidget))
    }
  }

  private fun buildScreen(node: ProtobufNode): Screen {
    checkChildrenScope<UiScope.Section>(node)
    val sections = node.children.map(::buildSection)

    val scope = checkScope<UiScope.Screen>(node)
    val type = Screen.Type.fromValue(scope.type)
        ?: throw BffUiCodegenException("""${scope.type} is not a valid Screen.Type tag.""")

    return Screen(
      id = node.id,
      attributes = attributeResolverResolve(node.attributes),
      actions = actionResolverResolve(node.actions),
      type = type,
      sections = sections,
    )
  }

  private fun buildSection(node: ProtobufNode): Section {
    checkChildrenScope<UiScope.Widget>(node)
    val widgets = node.children.map(::buildWidget)
    val stackDirection = checkType<Section.StackDirection>(node.protoField(5, require = true))
    val debugName = checkTypeIfNotNull<String>(node.protoField(6, require = false))

    val scope = checkScope<UiScope.Section>(node)
    val type = Section.Type.fromValue(scope.type)
        ?: throw BffUiCodegenException("""${scope.type} is not a valid Section.Type tag.""")

    return Section(
      id = node.id,
      attributes = attributeResolverResolve(node.attributes),
      actions = actionResolverResolve(node.actions),
      type = type,
      widgets = widgets,
      stack_direction = stackDirection,
      debug_name = debugName,
    )
  }

  private fun buildWidget(node: ProtobufNode): Widget {
    checkChildrenScope<UiScope.ChildWidgetOrComponent>(node)
    val scope = checkScope<UiScope.Widget>(node)
    val isSticky = checkType<Boolean>(node.protoField(4, require = true))

    return when (val contentTag = scope.contentType) {
      1_000 -> Widget(
        id = node.id,
        attributes = attributeResolverResolve(node.attributes),
        actions = actionResolverResolve(node.actions),
        is_sticky = isSticky,
        search_hospital_a = searchHospitalA(node),
      )
      2_000 -> Widget(
        id = node.id,
        attributes = attributeResolverResolve(node.attributes),
        actions = actionResolverResolve(node.actions),
        is_sticky = isSticky,
        cell_divider = cellDivider(node),
      )
      else -> throw BffUiCodegenException("""$contentTag is not a valid Widget's content tag.""")
    }
  }
}
