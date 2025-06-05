// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentCompositeKeyHash
import bff.ui.ProtobufApplier
import bff.ui.ProtobufFieldTag
import bff.ui.ProtobufNode
import bff.ui.UiScope.ChildWidgetOrComponent
import bff.ui.UiScopeMarker
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.String
import kotlin.Unit
import protobuf.source.component.CellColor
import protobuf.source.component.CellDividerComponent
import protobuf.source.component.CellTextStyle

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsScope {
  @Composable
  public fun AEventChildWidget(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    title: @Composable SearchHospitalAAEventItemsTitleScope.() -> Unit = {},
    cost: @Composable SearchHospitalAAEventItemsCostScope.() -> Unit = {},
    divider: @Composable SearchHospitalAAEventItemsDividerScope.() -> Unit,
    bEventItems: @Composable SearchHospitalAAEventItemsBEventItemsScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAHospitalNameScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAInfoTextScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalADividerScope {
  @Composable
  public fun CellDividerComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalADividerTextScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsTitleScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsCostScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsDividerScope {
  @Composable
  public fun CellDividerComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemsDividerTextScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsBEventItemsScope {
  @Composable
  public fun BEventChildWidget(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    title: @Composable SearchHospitalAAEventItemsBEventItemsTitleScope.() -> Unit = {},
    cost: @Composable SearchHospitalAAEventItemsBEventItemsCostScope.() -> Unit = {},
    divider: @Composable SearchHospitalAAEventItemsBEventItemsDividerScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalADividerTextScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsDividerTextScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsBEventItemsTitleScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsBEventItemsCostScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsBEventItemsDividerScope {
  @Composable
  public fun CellDividerComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemsBEventItemsDividerTextScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemsBEventItemsDividerTextScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

internal data object SearchHospitalAAEventItemsScopeProvider : SearchHospitalAAEventItemsScope {
  @Composable
  override fun AEventChildWidget(
    attributes: Attributes,
    actions: Actions,
    title: @Composable SearchHospitalAAEventItemsTitleScope.() -> Unit,
    cost: @Composable SearchHospitalAAEventItemsCostScope.() -> Unit,
    divider: @Composable SearchHospitalAAEventItemsDividerScope.() -> Unit,
    bEventItems: @Composable SearchHospitalAAEventItemsBEventItemsScope.() -> Unit,
  ) {
    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          ChildWidgetOrComponent(4),
          currentCompositeKeyHash,
        )
      },
    ) {
      SearchHospitalAAEventItemsTitleScopeProvider.title()
      SearchHospitalAAEventItemsCostScopeProvider.cost()
      SearchHospitalAAEventItemsDividerScopeProvider.divider()
      SearchHospitalAAEventItemsBEventItemsScopeProvider.bEventItems()
    }
  }
}

internal data object SearchHospitalAHospitalNameScopeProvider : SearchHospitalAHospitalNameScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(1),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAInfoTextScopeProvider : SearchHospitalAInfoTextScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(2),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalADividerScopeProvider : SearchHospitalADividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalADividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          ChildWidgetOrComponent(3),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
        }
      },
    ) {
      SearchHospitalADividerTextScopeProvider.text()
    }
  }
}

internal data object SearchHospitalAAEventItemsTitleScopeProvider : SearchHospitalAAEventItemsTitleScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(4),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAAEventItemsCostScopeProvider : SearchHospitalAAEventItemsCostScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(5),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAAEventItemsDividerScopeProvider : SearchHospitalAAEventItemsDividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemsDividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          ChildWidgetOrComponent(6),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
        }
      },
    ) {
      SearchHospitalAAEventItemsDividerTextScopeProvider.text()
    }
  }
}

internal data object SearchHospitalAAEventItemsBEventItemsScopeProvider : SearchHospitalAAEventItemsBEventItemsScope {
  @Composable
  override fun BEventChildWidget(
    attributes: Attributes,
    actions: Actions,
    title: @Composable SearchHospitalAAEventItemsBEventItemsTitleScope.() -> Unit,
    cost: @Composable SearchHospitalAAEventItemsBEventItemsCostScope.() -> Unit,
    divider: @Composable SearchHospitalAAEventItemsBEventItemsDividerScope.() -> Unit,
  ) {
    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          ChildWidgetOrComponent(7),
          currentCompositeKeyHash,
        )
      },
    ) {
      SearchHospitalAAEventItemsBEventItemsTitleScopeProvider.title()
      SearchHospitalAAEventItemsBEventItemsCostScopeProvider.cost()
      SearchHospitalAAEventItemsBEventItemsDividerScopeProvider.divider()
    }
  }
}

internal data object SearchHospitalADividerTextScopeProvider : SearchHospitalADividerTextScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(3),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAAEventItemsDividerTextScopeProvider : SearchHospitalAAEventItemsDividerTextScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(3),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAAEventItemsBEventItemsTitleScopeProvider : SearchHospitalAAEventItemsBEventItemsTitleScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(4),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAAEventItemsBEventItemsCostScopeProvider : SearchHospitalAAEventItemsBEventItemsCostScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(5),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}

internal data object SearchHospitalAAEventItemsBEventItemsDividerScopeProvider : SearchHospitalAAEventItemsBEventItemsDividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemsBEventItemsDividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

    val currentCompositeKeyHash = currentCompositeKeyHash

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          ChildWidgetOrComponent(6),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
        }
      },
    ) {
      SearchHospitalAAEventItemsBEventItemsDividerTextScopeProvider.text()
    }
  }
}

internal data object SearchHospitalAAEventItemsBEventItemsDividerTextScopeProvider : SearchHospitalAAEventItemsBEventItemsDividerTextScope {
  @Composable
  override fun CellTextComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  ) {
    if (style == CellTextStyle.CELL_TEXT_STYLE_UNSPECIFIED)
        error("""
        |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
        |Protobuf field를 optional로 만들고 null을 제공하세요. (style)
        """.trimMargin())

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
          ChildWidgetOrComponent(3),
          currentCompositeKeyHash,
        )
        init {
          data[ProtobufFieldTag(2)] = style
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    ) {
    }
  }
}
