package bff.ui

import dev.drewhamilton.poko.Poko

internal sealed interface UiScope {
  data object Root : UiScope
  @Poko class Screen(val type: Int) : UiScope
  @Poko class Section(val type: Int) : UiScope
  @Poko class Widget(val contentType: Int) : UiScope
  @Poko class ChildWidgetOrComponent(val tag: Int) : UiScope
}

internal fun UiScope.idString(): String =
  when (this) {
    is UiScope.Root -> ""
    is UiScope.Screen -> "screen#$type"
    is UiScope.Section -> "section#$type"
    is UiScope.Widget -> "widget#$contentType"
    is UiScope.ChildWidgetOrComponent -> "childWidgetOrComponent#$tag"
  }
