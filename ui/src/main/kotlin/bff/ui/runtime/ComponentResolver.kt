// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.helper.checkType
import bff.ui.helper.childOfScope
import bff.ui.helper.componentBase
import bff.ui.protoField
import protobuf.source.component.CellColor
import protobuf.source.component.CellDividerComponent
import protobuf.source.component.CellTextComponent
import protobuf.source.component.CellTextStyle

internal object ComponentResolver {
  internal fun searchHospitalACellText(node: ProtobufNode): CellTextComponent {
    val style = checkType<CellTextStyle>(node.protoField(2, require = true))
    val color = checkType<CellColor>(node.protoField(3, require = true))
    val text = checkType<String>(node.protoField(4, require = true))

    return CellTextComponent(
      base = node.componentBase(),
      style = style,
      color = color,
      text = text,
    )
  }

  internal fun cellDividerCellText(node: ProtobufNode): CellTextComponent {
    val style = checkType<CellTextStyle>(node.protoField(2, require = true))
    val color = checkType<CellColor>(node.protoField(3, require = true))
    val text = checkType<String>(node.protoField(4, require = true))

    return CellTextComponent(
      base = node.componentBase(),
      style = style,
      color = color,
      text = text,
    )
  }

  internal fun searchHospitalACellDivider(node: ProtobufNode): CellDividerComponent {
    val style = checkType<CellDividerComponent.Style>(node.protoField(2, require = true))
    val text = node.childOfScope(UiScope.ChildWidgetOrComponent(3))

    return CellDividerComponent(
      base = node.componentBase(),
      style = style,
      text = ComponentResolver.cellDividerCellText(text),
    )
  }

  internal fun aEventCellText(node: ProtobufNode): CellTextComponent {
    val style = checkType<CellTextStyle>(node.protoField(2, require = true))
    val color = checkType<CellColor>(node.protoField(3, require = true))
    val text = checkType<String>(node.protoField(4, require = true))

    return CellTextComponent(
      base = node.componentBase(),
      style = style,
      color = color,
      text = text,
    )
  }

  internal fun aEventCellDivider(node: ProtobufNode): CellDividerComponent {
    val style = checkType<CellDividerComponent.Style>(node.protoField(2, require = true))
    val text = node.childOfScope(UiScope.ChildWidgetOrComponent(3))

    return CellDividerComponent(
      base = node.componentBase(),
      style = style,
      text = ComponentResolver.cellDividerCellText(text),
    )
  }

  internal fun bEventCellText(node: ProtobufNode): CellTextComponent {
    val style = checkType<CellTextStyle>(node.protoField(2, require = true))
    val color = checkType<CellColor>(node.protoField(3, require = true))
    val text = checkType<String>(node.protoField(4, require = true))

    return CellTextComponent(
      base = node.componentBase(),
      style = style,
      color = color,
      text = text,
    )
  }

  internal fun bEventCellDivider(node: ProtobufNode): CellDividerComponent {
    val style = checkType<CellDividerComponent.Style>(node.protoField(2, require = true))
    val text = node.childOfScope(UiScope.ChildWidgetOrComponent(3))

    return CellDividerComponent(
      base = node.componentBase(),
      style = style,
      text = ComponentResolver.cellDividerCellText(text),
    )
  }

  internal fun cellDividerCellDivider(node: ProtobufNode): CellDividerComponent {
    val style = checkType<CellDividerComponent.Style>(node.protoField(2, require = true))
    val text = node.childOfScope(UiScope.ChildWidgetOrComponent(3))

    return CellDividerComponent(
      base = node.componentBase(),
      style = style,
      text = ComponentResolver.cellDividerCellText(text),
    )
  }
}
