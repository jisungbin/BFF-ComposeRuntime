package protobuf.generator

import com.squareup.kotlinpoet.ClassName

internal object Names {
  internal const val UI_PACKAGE = "bff.ui"

  internal val AttributeCn = ClassName(UI_PACKAGE, "Attribute")
  internal val AttributesCn = ClassName(UI_PACKAGE, "Attributes")

  internal val ActionsCn = ClassName(UI_PACKAGE, "Actions")

  internal val UiScopeCn = ClassName(UI_PACKAGE, "UiScope")
  internal val UiScopeMarkerCn = ClassName(UI_PACKAGE, "UiScopeMarker")

  internal val ProtobufNodeCn = ClassName(UI_PACKAGE, "ProtobufNode")
  internal val ProtobufFieldTagCn = ClassName(UI_PACKAGE, "ProtobufFieldTag")
  internal val ProtobufApplierCn = ClassName(UI_PACKAGE, "ProtobufApplier")

  internal val BffUiCodegenExceptionCn = ClassName(UI_PACKAGE, "BffUiCodegenException")
}