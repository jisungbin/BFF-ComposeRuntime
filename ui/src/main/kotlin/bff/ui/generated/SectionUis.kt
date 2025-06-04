// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentCompositeKeyHash
import bff.ui.ProtobufApplier
import bff.ui.ProtobufNode
import bff.ui.UiScopeMarker
import bff.ui.`internal`.ExtraField
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import protobuf.source.section.Section

@UiScopeMarker
public sealed interface SectionScope

private data object SectionScopeProvider : SectionScope

@Composable
@Suppress("UnusedReceiverParameter")
public fun ScreenScope.HeaderSection(
  attributes: Attributes = Attributes,
  actions: Actions = Actions,
  stackDirection: Section.StackDirection,
  debugName: String? = null,
  widgets: @Composable SectionScope.() -> Unit,
) {
  if (stackDirection == Section.StackDirection.STACK_DIRECTION_UNSPECIFIED)
      error("""
      |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
      |Protobuf field를 optional로 만들고 null을 제공하세요. (stackDirection)
      """.trimMargin())

  val currentCompositeKeyHash = currentCompositeKeyHash

  ComposeNode<ProtobufNode, ProtobufApplier>(
    factory = ProtobufNode.CONSTRUCTOR,
    update = {
      ProtobufNode.INIT(
          this,
          attributes,
          actions,
          bff.ui.UiScope.Section(1),
          currentCompositeKeyHash,
      )
      init {
        data[ExtraField(5)] = stackDirection
        if (debugName != null) data[ExtraField(6)] = debugName
      }
    },
  ) {
    SectionScopeProvider.widgets()
  }
}

@Composable
@Suppress("UnusedReceiverParameter")
public fun ScreenScope.BodySection(
  attributes: Attributes = Attributes,
  actions: Actions = Actions,
  stackDirection: Section.StackDirection,
  debugName: String? = null,
  widgets: @Composable SectionScope.() -> Unit,
) {
  if (stackDirection == Section.StackDirection.STACK_DIRECTION_UNSPECIFIED)
      error("""
      |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
      |Protobuf field를 optional로 만들고 null을 제공하세요. (stackDirection)
      """.trimMargin())

  val currentCompositeKeyHash = currentCompositeKeyHash

  ComposeNode<ProtobufNode, ProtobufApplier>(
    factory = ProtobufNode.CONSTRUCTOR,
    update = {
      ProtobufNode.INIT(
          this,
          attributes,
          actions,
          bff.ui.UiScope.Section(2),
          currentCompositeKeyHash,
      )
      init {
        data[ExtraField(5)] = stackDirection
        if (debugName != null) data[ExtraField(6)] = debugName
      }
    },
  ) {
    SectionScopeProvider.widgets()
  }
}

@Composable
@Suppress("UnusedReceiverParameter")
public fun ScreenScope.FooterSection(
  attributes: Attributes = Attributes,
  actions: Actions = Actions,
  stackDirection: Section.StackDirection,
  debugName: String? = null,
  widgets: @Composable SectionScope.() -> Unit,
) {
  if (stackDirection == Section.StackDirection.STACK_DIRECTION_UNSPECIFIED)
      error("""
      |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
      |Protobuf field를 optional로 만들고 null을 제공하세요. (stackDirection)
      """.trimMargin())

  val currentCompositeKeyHash = currentCompositeKeyHash

  ComposeNode<ProtobufNode, ProtobufApplier>(
    factory = ProtobufNode.CONSTRUCTOR,
    update = {
      ProtobufNode.INIT(
          this,
          attributes,
          actions,
          bff.ui.UiScope.Section(3),
          currentCompositeKeyHash,
      )
      init {
        data[ExtraField(5)] = stackDirection
        if (debugName != null) data[ExtraField(6)] = debugName
      }
    },
  ) {
    SectionScopeProvider.widgets()
  }
}
