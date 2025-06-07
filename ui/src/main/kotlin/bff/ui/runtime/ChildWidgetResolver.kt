// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.helper.ActionResolver
import bff.ui.helper.childOfScope
import bff.ui.helper.childOfScopeOrNull
import bff.ui.helper.childrenOfScope
import protobuf.source.widget.SearchHospitalAWidgetContent

internal object ChildWidgetResolver {
  internal fun aEventBEvent(node: ProtobufNode): SearchHospitalAWidgetContent.BEventChildWidget {
    val title = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(4))
    val cost = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(5))
    val divider = node.childOfScope(UiScope.ChildWidgetOrComponent(6))

    return SearchHospitalAWidgetContent.BEventChildWidget(
      id = node.id,
      attributes = AttributeResolver.resolve(node.attributes),
      actions = ActionResolver.resolve(node.actions),
      title = title?.let(ComponentResolver::bEventCellText),
      cost = cost?.let(ComponentResolver::bEventCellText),
      divider = ComponentResolver.bEventCellDivider(divider),
    )
  }

  internal fun searchHospitalAAEvent(node: ProtobufNode): SearchHospitalAWidgetContent.AEventChildWidget {
    val title = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(4))
    val cost = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(5))
    val divider = node.childOfScope(UiScope.ChildWidgetOrComponent(6))
    val bEventItems = node.childrenOfScope(UiScope.ChildWidgetOrComponent(7))

    return SearchHospitalAWidgetContent.AEventChildWidget(
      id = node.id,
      attributes = AttributeResolver.resolve(node.attributes),
      actions = ActionResolver.resolve(node.actions),
      title = title?.let(ComponentResolver::aEventCellText),
      cost = cost?.let(ComponentResolver::aEventCellText),
      divider = ComponentResolver.aEventCellDivider(divider),
      b_event_items = bEventItems.map(ChildWidgetResolver::aEventBEvent),
    )
  }
}
