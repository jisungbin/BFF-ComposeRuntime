package bffui.composeruntime.poc

import dev.drewhamilton.poko.Poko

sealed interface Kind {
  @Poko class Screen(val type: Int) : Kind
  @Poko class Section(val type: Int) : Kind
  @Poko class Widget(val identifier: Int) : Kind
  @Poko class ChildWidget(val identifier: Int) : Kind
  @Poko class Component(val identifier: Int) : Kind
}
