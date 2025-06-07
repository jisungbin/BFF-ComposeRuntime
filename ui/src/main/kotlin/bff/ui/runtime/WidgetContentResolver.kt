// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.helper.checkType
import bff.ui.helper.checkTypeIfNotNull
import bff.ui.helper.childOfScope
import bff.ui.helper.childOfScopeOrNull
import bff.ui.helper.childrenOfScope
import bff.ui.protoField
import protobuf.source.component.CellColor
import protobuf.source.widget.CellDividerWidgetContent
import protobuf.source.widget.SearchHospitalAWidgetContent

internal object WidgetContentResolver {
  internal fun searchHospitalA(node: ProtobufNode): SearchHospitalAWidgetContent {
    val hospitalName = node.childrenOfScope(UiScope.ChildWidgetOrComponent(1))
    val infoText = node.childrenOfScope(UiScope.ChildWidgetOrComponent(2))
    val divider = node.childOfScope(UiScope.ChildWidgetOrComponent(3))
    val aEventItem = node.childOfScopeOrNull(UiScope.ChildWidgetOrComponent(4))

    return SearchHospitalAWidgetContent(
      hospital_name = hospitalName.map(ComponentResolver::cellText),
      info_text = infoText.map(ComponentResolver::cellText),
      divider = ComponentResolver.cellDivider(divider),
      a_event_item = aEventItem?.let(ChildWidgetResolver::aEvent),
    )
  }

  internal fun cellDivider(node: ProtobufNode): CellDividerWidgetContent {
    val color = checkType<CellColor>(node.protoField(2, require = true))
    val debugName = checkTypeIfNotNull<String>(node.protoField(3, require = false))
    val divider = node.childOfScope(UiScope.ChildWidgetOrComponent(1))

    return CellDividerWidgetContent(
      color = color,
      debug_name = debugName,
      divider = ComponentResolver.cellDivider(divider),
    )
  }
}
