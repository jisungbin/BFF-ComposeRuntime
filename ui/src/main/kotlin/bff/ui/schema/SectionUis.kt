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
import protobuf.source.section.Section

@UiScopeMarker
public sealed interface SectionScope {
  @Composable
  public fun HeaderSection(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    stackDirection: Section.StackDirection,
    debugName: String? = null,
    widgets: @Composable WidgetScope.() -> Unit,
  )

  @Composable
  public fun BodySection(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    stackDirection: Section.StackDirection,
    debugName: String? = null,
    widgets: @Composable WidgetScope.() -> Unit,
  )

  @Composable
  public fun FooterSection(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    stackDirection: Section.StackDirection,
    debugName: String? = null,
    widgets: @Composable WidgetScope.() -> Unit,
  )
}

internal data object SectionScopeProvider : SectionScope {
  @Composable
  override fun HeaderSection(
    attributes: Attributes,
    actions: Actions,
    stackDirection: Section.StackDirection,
    debugName: String?,
    widgets: @Composable WidgetScope.() -> Unit,
  ) {
    if (stackDirection == Section.StackDirection.STACK_DIRECTION_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. " +
          "(HeaderSection(...) 함수의 stackDirection 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.Section(1),
          applier,
        )
        init {
          data[ProtobufFieldTag(5)] = stackDirection
          if (debugName != null) data[ProtobufFieldTag(6)] = debugName
        }
      },
    ) {
      WidgetScopeProvider.widgets()
    }
  }

  @Composable
  override fun BodySection(
    attributes: Attributes,
    actions: Actions,
    stackDirection: Section.StackDirection,
    debugName: String?,
    widgets: @Composable WidgetScope.() -> Unit,
  ) {
    if (stackDirection == Section.StackDirection.STACK_DIRECTION_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. " +
          "(BodySection(...) 함수의 stackDirection 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.Section(2),
          applier,
        )
        init {
          data[ProtobufFieldTag(5)] = stackDirection
          if (debugName != null) data[ProtobufFieldTag(6)] = debugName
        }
      },
    ) {
      WidgetScopeProvider.widgets()
    }
  }

  @Composable
  override fun FooterSection(
    attributes: Attributes,
    actions: Actions,
    stackDirection: Section.StackDirection,
    debugName: String?,
    widgets: @Composable WidgetScope.() -> Unit,
  ) {
    if (stackDirection == Section.StackDirection.STACK_DIRECTION_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. " +
          "(FooterSection(...) 함수의 stackDirection 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.Section(3),
          applier,
        )
        init {
          data[ProtobufFieldTag(5)] = stackDirection
          if (debugName != null) data[ProtobufFieldTag(6)] = debugName
        }
      },
    ) {
      WidgetScopeProvider.widgets()
    }
  }
}
