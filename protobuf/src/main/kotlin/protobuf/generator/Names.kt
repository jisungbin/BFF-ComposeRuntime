package protobuf.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

internal typealias ProtoAttributes = protobuf.source.attributes.Attributes

internal object Names {
  internal const val UI_PACKAGE_NAME = "bff.ui"
  internal const val HELPER_PACKAGE_NAME = "bff.ui.helper"

  internal val AttributeCn = ClassName(UI_PACKAGE_NAME, "Attribute")
  internal val AttributesCn = ClassName(UI_PACKAGE_NAME, "Attributes")

  internal val ActionsCn = ClassName(UI_PACKAGE_NAME, "Actions")

  internal val UiScopeCn = ClassName(UI_PACKAGE_NAME, "UiScope")
  internal val UiScopeMarkerCn = ClassName(UI_PACKAGE_NAME, "UiScopeMarker")

  internal val ProtobufNodeCn = ClassName(UI_PACKAGE_NAME, "ProtobufNode")
  internal val ProtobufApplierCn = ClassName(UI_PACKAGE_NAME, "ProtobufApplier")
  internal val ProtobufFieldTagCn = ClassName(UI_PACKAGE_NAME, "ProtobufFieldTag")

  internal val BffUiCodegenExceptionCn = ClassName(UI_PACKAGE_NAME, "BffUiCodegenException")


  // Helper functions

  internal val childOfScopeMn = MemberName(HELPER_PACKAGE_NAME, "childOfScope", isExtension = true)
  internal val childOfScopeOrNullMn = MemberName(HELPER_PACKAGE_NAME, "childOfScopeOrNull", isExtension = true)
  internal val childrenOfScopeMn = MemberName(HELPER_PACKAGE_NAME, "childrenOfScope", isExtension = true)
  internal val componentBaseMn = MemberName(HELPER_PACKAGE_NAME, "componentBase", isExtension = true)

  internal val checkTypeMn = MemberName(HELPER_PACKAGE_NAME, "checkType")
  internal val checkTypeIfNotNullMn = MemberName(HELPER_PACKAGE_NAME, "checkTypeIfNotNull")
  internal val checkScopeMn = MemberName(HELPER_PACKAGE_NAME, "checkScope")
  internal val checkChildrenScopeMn = MemberName(HELPER_PACKAGE_NAME, "checkChildrenScope")
}
