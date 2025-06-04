// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.attribute

import androidx.collection.mutableIntObjectMapOf
import kotlin.Any
import kotlin.Float

public fun Attributes.padding(
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
  area: protobuf.source.attributes.Attributes.SizeArea,
  defaultValue: Float,
  minValue: Float? = null,
  maxValue: Float? = null,
): Attributes {
  val arguments = mutableIntObjectMapOf<Any>()
  arguments[1] = area
  arguments[3] = defaultValue
  if (minValue != null) arguments[2] = minValue
  if (maxValue != null) arguments[4] = maxValue
  return this then Attribute(2, arguments)
}
