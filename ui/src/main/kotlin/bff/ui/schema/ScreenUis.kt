// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import bff.ui.Actions
import bff.ui.Attributes
import bff.ui.ProtobufApplier
import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.UiScopeMarker
import kotlin.Unit

@UiScopeMarker
public sealed interface ScreenScope {
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

internal data object ScreenScopeProvider : ScreenScope {
  @Composable
  override fun RootScreen(
    attributes: Attributes,
    actions: Actions,
    sections: @Composable SectionScope.() -> Unit,
  ) {
    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.Screen(1),
          applier,
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
    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.Screen(2),
          applier,
        )
      },
    ) {
      SectionScopeProvider.sections()
    }
  }
}
