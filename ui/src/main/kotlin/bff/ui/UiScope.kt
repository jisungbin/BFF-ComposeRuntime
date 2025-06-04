package bff.ui

import dev.drewhamilton.poko.Poko

public sealed interface UiScope {
  @Poko public class Screen(public val type: Int) : UiScope
  @Poko public class Section(public val type: Int) : UiScope
  @Poko public class Widget(public val contentType: Int) : UiScope
  @Poko public class ChildWidget(public val identifier: Int) : UiScope
  @Poko public class Component(public val identifier: Int) : UiScope
}
