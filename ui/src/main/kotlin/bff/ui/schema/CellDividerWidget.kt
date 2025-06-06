// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import bff.ui.Actions
import bff.ui.Attributes
import bff.ui.ProtobufApplier
import bff.ui.ProtobufFieldTag
import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.UiScopeMarker
import protobuf.source.component.CellColor
import protobuf.source.component.CellDividerComponent
import protobuf.source.component.CellTextStyle

@UiScopeMarker
public sealed interface CellDividerDividerScope {
  @Composable
  public fun CellDividerComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellDividerComponent.Style,
    text: @Composable CellDividerDividerTextScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface CellDividerDividerTextScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

internal data object CellDividerDividerScopeProvider : CellDividerDividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable CellDividerDividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. " +
          "(CellDividerComponent(...) 함수의 style 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(1),
          applier,
        )
        init {
          data[ProtobufFieldTag(2)] = style
        }
      },
    ) {
      CellDividerDividerTextScopeProvider.text()
    }
  }
}

internal data object CellDividerDividerTextScopeProvider : CellDividerDividerTextScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. " +
          "(CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. " +
          "(CellTextComponent(...) 함수의 color 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(3),
          applier,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    )
  }
}
