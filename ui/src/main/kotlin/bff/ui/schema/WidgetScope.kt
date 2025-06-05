// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentCompositeKeyHash
import bff.ui.ProtobufApplier
import bff.ui.ProtobufFieldTag
import bff.ui.ProtobufNode
import bff.ui.UiScope.Widget
import bff.ui.UiScopeMarker
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.String
import kotlin.Unit
import protobuf.source.component.CellColor

@UiScopeMarker
public sealed interface WidgetScope {
  @Composable
  public fun SearchHospitalAWidget(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    aEventItems: @Composable SearchHospitalAAEventItemsScope.() -> Unit = {},
    hospitalName: @Composable SearchHospitalAHospitalNameScope.() -> Unit,
    infoText: @Composable SearchHospitalAInfoTextScope.() -> Unit,
    divider: @Composable SearchHospitalADividerScope.() -> Unit,
  )

  @Composable
  public fun CellDividerWidget(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    color: CellColor,
    debugName: String? = null,
    divider: @Composable CellDividerDividerScope.() -> Unit,
  )
}

internal data object WidgetScopeProvider : WidgetScope {
  @Composable
  override fun SearchHospitalAWidget(
    attributes: Attributes,
    actions: Actions,
    aEventItems: @Composable SearchHospitalAAEventItemsScope.() -> Unit,
    hospitalName: @Composable SearchHospitalAHospitalNameScope.() -> Unit,
    infoText: @Composable SearchHospitalAInfoTextScope.() -> Unit,
    divider: @Composable SearchHospitalADividerScope.() -> Unit,
  ) {
    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          Widget(1_000),
          currentCompositeKeyHash,
        )
      },
    ) {
      SearchHospitalAHospitalNameScopeProvider.hospitalName()
      SearchHospitalAInfoTextScopeProvider.infoText()
      SearchHospitalADividerScopeProvider.divider()
      SearchHospitalAAEventItemsScopeProvider.aEventItems()
    }
  }

  @Composable
  override fun CellDividerWidget(
    attributes: Attributes,
    actions: Actions,
    color: CellColor,
    debugName: String?,
    divider: @Composable CellDividerDividerScope.() -> Unit,
  ) {
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (color)
        """.trimMargin())

    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          Widget(2_000),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = color
          if (debugName != null) data[ProtobufFieldTag(3)] = debugName
        }
      },
    ) {
      CellDividerDividerScopeProvider.divider()
    }
  }
}
