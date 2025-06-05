// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentCompositeKeyHash
import bff.ui.ProtobufApplier
import bff.ui.ProtobufNode
import bff.ui.UiScope.Screen
import bff.ui.UiScopeMarker
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.Unit

@UiScopeMarker
public sealed interface ProtobufUiScope {
  @Composable
  public fun RootScreen(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    sections: @Composable SectionScope.() -> Unit,
  )

  @Composable
  public fun BottomSheetScreen(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    sections: @Composable SectionScope.() -> Unit,
  )
}

internal data object ProtobufUiScopeProvider : ProtobufUiScope {
  @Composable
  override fun RootScreen(
    attributes: Attributes,
    actions: Actions,
    sections: @Composable SectionScope.() -> Unit,
  ) {
    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          Screen(1),
          currentCompositeKeyHash,
        )
      },
    ) {
      SectionScopeProvider.sections()
    }
  }

  @Composable
  override fun BottomSheetScreen(
    attributes: Attributes,
    actions: Actions,
    sections: @Composable SectionScope.() -> Unit,
  ) {
    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          Screen(2),
          currentCompositeKeyHash,
        )
      },
    ) {
      SectionScopeProvider.sections()
    }
  }
}
