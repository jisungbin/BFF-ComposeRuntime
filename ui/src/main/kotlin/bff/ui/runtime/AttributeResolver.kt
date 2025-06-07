// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import androidx.collection.IntObjectMap
import bff.ui.BffUiCodegenException
import bff.ui.helper.checkType
import bff.ui.helper.checkTypeIfNotNull
import bff.ui.Attributes as UiAttributes
import protobuf.source.attributes.Attributes as ProtoAttributes
import protobuf.source.attributes.Attributes.PaddingAttribute as ProtoPaddingAttribute
import protobuf.source.attributes.Attributes.SizeAttribute as ProtoSizeAttribute

internal object AttributeResolver {
  internal fun resolve(attributes: UiAttributes): List<ProtoAttributes> {
    if (attributes === UiAttributes) return emptyList()

    return attributes.value.fold(mutableListOf()) { acc, element ->
      acc += when (val tag = element.index) {
        1 -> ProtoAttributes(layout_padding = layoutPadding(element.arguments))
        2 -> ProtoAttributes(size = size(element.arguments))
        else -> throw BffUiCodegenException("""$tag is not a valid attribute tag.""")
      }
      acc
    }
  }

  private fun layoutPadding(arguments: IntObjectMap<Any>): ProtoPaddingAttribute = ProtoPaddingAttribute(
    top = checkTypeIfNotNull<Float>(arguments[1]),
    leading = checkTypeIfNotNull<Float>(arguments[2]),
    bottom = checkTypeIfNotNull<Float>(arguments[3]),
    trailing = checkTypeIfNotNull<Float>(arguments[4]),
  )

  private fun size(arguments: IntObjectMap<Any>): ProtoSizeAttribute = ProtoSizeAttribute(
    area = checkType<ProtoAttributes.SizeArea>(arguments[1]),
    min_value = checkTypeIfNotNull<Float>(arguments[2]),
    default_value = checkType<Float>(arguments[3]),
    max_value = checkTypeIfNotNull<Float>(arguments[4]),
  )
}
