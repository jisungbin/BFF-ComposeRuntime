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
public sealed interface SearchHospitalAAEventItemScope {
  @Composable
  public fun AEventChildWidget(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    title: @Composable SearchHospitalAAEventItemTitleScope.() -> Unit = {},
    cost: @Composable SearchHospitalAAEventItemCostScope.() -> Unit = {},
    divider: @Composable SearchHospitalAAEventItemDividerScope.() -> Unit,
    bEventItems: @Composable SearchHospitalAAEventItemBEventItemsScope.() -> Unit,
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
public sealed interface SearchHospitalAAEventItemTitleScope {
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
public sealed interface SearchHospitalAAEventItemCostScope {
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
public sealed interface SearchHospitalAAEventItemDividerScope {
  @Composable
  public fun CellDividerComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemDividerTextScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemBEventItemsScope {
  @Composable
  public fun BEventChildWidget(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    title: @Composable SearchHospitalAAEventItemBEventItemsTitleScope.() -> Unit = {},
    cost: @Composable SearchHospitalAAEventItemBEventItemsCostScope.() -> Unit = {},
    divider: @Composable SearchHospitalAAEventItemBEventItemsDividerScope.() -> Unit,
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
public sealed interface SearchHospitalAAEventItemDividerTextScope {
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
public sealed interface SearchHospitalAAEventItemBEventItemsTitleScope {
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
public sealed interface SearchHospitalAAEventItemBEventItemsCostScope {
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
public sealed interface SearchHospitalAAEventItemBEventItemsDividerScope {
  @Composable
  public fun CellDividerComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemBEventItemsDividerTextScope.() -> Unit,
  )
}

@UiScopeMarker
public sealed interface SearchHospitalAAEventItemBEventItemsDividerTextScope {
  @Composable
  public fun CellTextComponent(
    attributes: Attributes = Attributes,
    actions: Actions = Actions,
    style: CellTextStyle,
    color: CellColor,
    text: String,
  )
}

internal data object SearchHospitalAAEventItemScopeProvider : SearchHospitalAAEventItemScope {
  @Composable
  override fun AEventChildWidget(
    attributes: Attributes,
    actions: Actions,
    title: @Composable SearchHospitalAAEventItemTitleScope.() -> Unit,
    cost: @Composable SearchHospitalAAEventItemCostScope.() -> Unit,
    divider: @Composable SearchHospitalAAEventItemDividerScope.() -> Unit,
    bEventItems: @Composable SearchHospitalAAEventItemBEventItemsScope.() -> Unit,
  ) {
    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(4),
          applier,
        )
      },
    ) {
      SearchHospitalAAEventItemTitleScopeProvider.title()
      SearchHospitalAAEventItemCostScopeProvider.cost()
      SearchHospitalAAEventItemDividerScopeProvider.divider()
      SearchHospitalAAEventItemBEventItemsScopeProvider.bEventItems()
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
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
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
          data[ProtobufFieldTag(3)] = color
          data[ProtobufFieldTag(4)] = text
        }
      },
    )
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
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(2),
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

internal data object SearchHospitalADividerScopeProvider : SearchHospitalADividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalADividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellDividerComponent(...) 함수의 style 인자)",
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
        }
      },
    ) {
      SearchHospitalADividerTextScopeProvider.text()
    }
  }
}

internal data object SearchHospitalAAEventItemTitleScopeProvider : SearchHospitalAAEventItemTitleScope {
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
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(4),
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

internal data object SearchHospitalAAEventItemCostScopeProvider : SearchHospitalAAEventItemCostScope {
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
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(5),
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

internal data object SearchHospitalAAEventItemDividerScopeProvider : SearchHospitalAAEventItemDividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemDividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellDividerComponent(...) 함수의 style 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(6),
          applier,
        )
        init {
          data[ProtobufFieldTag(2)] = style
        }
      },
    ) {
      SearchHospitalAAEventItemDividerTextScopeProvider.text()
    }
  }
}

internal data object SearchHospitalAAEventItemBEventItemsScopeProvider : SearchHospitalAAEventItemBEventItemsScope {
  @Composable
  override fun BEventChildWidget(
    attributes: Attributes,
    actions: Actions,
    title: @Composable SearchHospitalAAEventItemBEventItemsTitleScope.() -> Unit,
    cost: @Composable SearchHospitalAAEventItemBEventItemsCostScope.() -> Unit,
    divider: @Composable SearchHospitalAAEventItemBEventItemsDividerScope.() -> Unit,
  ) {
    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(7),
          applier,
        )
      },
    ) {
      SearchHospitalAAEventItemBEventItemsTitleScopeProvider.title()
      SearchHospitalAAEventItemBEventItemsCostScopeProvider.cost()
      SearchHospitalAAEventItemBEventItemsDividerScopeProvider.divider()
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
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
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

internal data object SearchHospitalAAEventItemDividerTextScopeProvider : SearchHospitalAAEventItemDividerTextScope {
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
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
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

internal data object SearchHospitalAAEventItemBEventItemsTitleScopeProvider : SearchHospitalAAEventItemBEventItemsTitleScope {
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
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(4),
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

internal data object SearchHospitalAAEventItemBEventItemsCostScopeProvider : SearchHospitalAAEventItemBEventItemsCostScope {
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
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(5),
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

internal data object SearchHospitalAAEventItemBEventItemsDividerScopeProvider : SearchHospitalAAEventItemBEventItemsDividerScope {
  @Composable
  override fun CellDividerComponent(
    attributes: Attributes,
    actions: Actions,
    style: CellDividerComponent.Style,
    text: @Composable SearchHospitalAAEventItemBEventItemsDividerTextScope.() -> Unit,
  ) {
    if (style == CellDividerComponent.Style.STYLE_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellDividerComponent(...) 함수의 style 인자)",
      )

    val applier = currentComposer.applier as ProtobufApplier

    ComposeNode<ProtobufNode, ProtobufApplier>(
      factory = ProtobufNode.CONSTRUCTOR,
      update = {
        ProtobufNode.INIT(
          this,
          attributes,
          actions,
          UiScope.ChildWidgetOrComponent(6),
          applier,
        )
        init {
          data[ProtobufFieldTag(2)] = style
        }
      },
    ) {
      SearchHospitalAAEventItemBEventItemsDividerTextScopeProvider.text()
    }
  }
}

internal data object SearchHospitalAAEventItemBEventItemsDividerTextScopeProvider : SearchHospitalAAEventItemBEventItemsDividerTextScope {
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
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 style 인자)",
      )
    if (color == CellColor.CELL_COLOR_UNSPECIFIED)
      throw IllegalArgumentException(
        "BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우 " +
          "Protobuf field를 optional로 만들고 null을 제공하세요. (CellTextComponent(...) 함수의 color 인자)",
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
