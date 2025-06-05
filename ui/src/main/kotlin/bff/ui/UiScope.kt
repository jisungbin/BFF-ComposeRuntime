package bff.ui

import dev.drewhamilton.poko.Poko

internal sealed interface UiScope {
  @Poko class Screen(val type: Int) : UiScope
  @Poko class Section(val type: Int) : UiScope
  @Poko class Widget(val contentType: Int) : UiScope
  @Poko class ChildWidgetOrComponent(val tag: Int) : UiScope
}
