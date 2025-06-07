// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.schema

import androidx.collection.mutableIntObjectMapOf
import bff.ui.Attribute
import bff.ui.Attributes
import bff.ui.then
import kotlin.Any
import kotlin.Float
import protobuf.source.attributes.Attributes as ProtoAttributes

public typealias ProtoSizeArea = ProtoAttributes.SizeArea

public fun Attributes.layoutPadding(
  top: Float? = null,
  leading: Float? = null,
  bottom: Float? = null,
  trailing: Float? = null,
): Attributes {
  val arguments = mutableIntObjectMapOf<Any>()
  if (top != null) arguments[1] = top
  if (leading != null) arguments[2] = leading
  if (bottom != null) arguments[3] = bottom
  if (trailing != null) arguments[4] = trailing

  return this then Attribute(1, arguments)
}

public fun Attributes.size(
  area: ProtoSizeArea,
  defaultValue: Float,
  minValue: Float? = null,
  maxValue: Float? = null,
): Attributes {
  if (area == ProtoSizeArea.SIZE_AREA_UNSPECIFIED)
      throw IllegalArgumentException("""
      |BFF UI에서 UNSPECIFIED 값의 직접 사용은 금지됩니다. 만약 지정할 값이 없는 경우
      |Protobuf field를 optional로 만들고 null을 제공하세요. (area)
      """.trimMargin())

  val arguments = mutableIntObjectMapOf<Any>()
  arguments[1] = area
  arguments[3] = defaultValue
  if (minValue != null) arguments[2] = minValue
  if (maxValue != null) arguments[4] = maxValue

  return this then Attribute(2, arguments)
}
