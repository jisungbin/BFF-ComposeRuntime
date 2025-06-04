// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.currentCompositeKeyHash
import bff.ui.ProtobufApplier
import bff.ui.ProtobufNode
import bff.ui.UiScope.Widget
import bff.ui.UiScopeMarker
import bff.ui.`internal`.ExtraField
import bff.ui.`internal`.LocalWidgetContentFieldTag
import bff.ui.`internal`.WidgetContentField
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.Boolean
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import protobuf.source.component.CellColor

@UiScopeMarker
public sealed interface SearchHospitalAEventChildWidgetScope

private data object SearchHospitalAEventChildWidgetScopeProvider : SearchHospitalAEventChildWidgetScope

@Composable
@Suppress("UnusedReceiverParameter")
public fun SectionScope.SearchHospitalAWidget(
  attributes: Attributes = Attributes,
  actions: Actions = Actions,
  isSticky: Boolean,
  hospitalName: @Composable CellTextComponentScope.() -> Unit = {},
  infoText: @Composable CellTextComponentScope.() -> Unit = {},
  divider: @Composable CellDividerComponentScope.() -> Unit,
  eventItems: @Composable SearchHospitalAEventChildWidgetScope.() -> Unit,
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
      init {
        data[ExtraField(4)] = isSticky
      }
    },
  ) {
    CompositionLocalProvider(LocalWidgetContentFieldTag provides 1) {
      CellTextComponentScopeProvider.hospitalName()
    }
    CompositionLocalProvider(LocalWidgetContentFieldTag provides 2) {
      CellTextComponentScopeProvider.infoText()
    }
    CompositionLocalProvider(LocalWidgetContentFieldTag provides 3) {
      CellDividerComponentScopeProvider.divider()
    }
    CompositionLocalProvider(LocalWidgetContentFieldTag provides 4) {
      SearchHospitalAEventChildWidgetScopeProvider.eventItems()
    }
  }
}

@Composable
@Suppress("UnusedReceiverParameter")
public fun SectionScope.CellDividerWidget(
  attributes: Attributes = Attributes,
  actions: Actions = Actions,
  isSticky: Boolean,
  color: CellColor,
  debugName: String? = null,
  divider: @Composable CellDividerComponentScope.() -> Unit,
) {
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
        data[ExtraField(4)] = isSticky
        data[WidgetContentField(2)] = color
        if (debugName != null) data[WidgetContentField(3)] = debugName
      }
    },
  ) {
    CompositionLocalProvider(LocalWidgetContentFieldTag provides 1) {
      CellDividerComponentScopeProvider.divider()
    }
  }
}
