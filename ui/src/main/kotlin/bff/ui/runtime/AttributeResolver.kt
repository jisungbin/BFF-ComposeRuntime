// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import androidx.collection.IntObjectMap
import bff.ui.BffUiCodegenException
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
    top = arguments[1] as? Float,
    leading = arguments[2] as? Float,
    bottom = arguments[3] as? Float,
    trailing = arguments[4] as? Float,
  )

  private fun size(arguments: IntObjectMap<Any>): ProtoSizeAttribute = ProtoSizeAttribute(
    area = arguments[1] as? ProtoAttributes.SizeArea ?: throw BffUiCodegenException(
      """
        |Expected type is protobuf.source.attributes.Attributes.SizeArea, but was 
        |${arguments[1]?.javaClass?.canonicalName}.
        """.trimMargin()
    ),
    min_value = arguments[2] as? Float,
    default_value = arguments[3] as? Float ?: throw BffUiCodegenException(
      """
        |Expected type is kotlin.Float, but was 
        |${arguments[3]?.javaClass?.canonicalName}.
        """.trimMargin()
    ),
    max_value = arguments[4] as? Float,
  )
}
