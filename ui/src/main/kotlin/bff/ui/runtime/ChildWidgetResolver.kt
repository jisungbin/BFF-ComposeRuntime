// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.helper.childOfScope
import bff.ui.helper.childOfScopeOrNull
import bff.ui.helper.childrenOfScope
import protobuf.source.widget.SearchHospitalAWidgetContent
import bff.ui.runtime.ActionResolver.resolve as actionResolverResolve
import bff.ui.runtime.AttributeResolver.resolve as attributeResolverResolve

internal object ChildWidgetResolver {
  internal fun bEvent(node: ProtobufNode): SearchHospitalAWidgetContent.BEventChildWidget {
    val title = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(4))
    val cost = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(5))
    val divider = node.childOfScope(UiScope.ChildWidgetOrComponent(6))

    return SearchHospitalAWidgetContent.BEventChildWidget(
      id = node.id,
      attributes = attributeResolverResolve(node.attributes),
      actions = actionResolverResolve(node.actions),
      title = title?.let(ComponentResolver::cellText),
      cost = cost?.let(ComponentResolver::cellText),
      divider = ComponentResolver.cellDivider(divider),
    )
  }

  internal fun aEvent(node: ProtobufNode): SearchHospitalAWidgetContent.AEventChildWidget {
    val title = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(4))
    val cost = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(5))
    val divider = node.childOfScope(UiScope.ChildWidgetOrComponent(6))
    val bEventItems = node.childrenOfScope(UiScope.ChildWidgetOrComponent(7))

    return SearchHospitalAWidgetContent.AEventChildWidget(
      id = node.id,
      attributes = attributeResolverResolve(node.attributes),
      actions = actionResolverResolve(node.actions),
      title = title?.let(ComponentResolver::cellText),
      cost = cost?.let(ComponentResolver::cellText),
      divider = ComponentResolver.cellDivider(divider),
      b_event_items = bEventItems.map(ChildWidgetResolver::bEvent),
    )
  }
}
