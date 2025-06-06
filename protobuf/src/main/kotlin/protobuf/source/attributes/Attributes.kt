// Code generated by Wire protocol buffer compiler, do not edit.
// Source: protobuf.source.attributes.Attributes in attributes/attributes.proto
@file:Suppress(
  "DEPRECATION",
  "RUNTIME_ANNOTATION_NOT_SUPPORTED",
)

package protobuf.source.attributes

import com.squareup.wire.EnumAdapter
import com.squareup.wire.FieldEncoding
import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.ReverseProtoWriter
import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.WireEnum
import com.squareup.wire.WireField
import com.squareup.wire.internal.JvmField
import com.squareup.wire.internal.JvmStatic
import com.squareup.wire.internal.countNonNull
import okio.ByteString

public class Attributes(
  @field:WireField(
    tag = 1,
    adapter = "protobuf.source.attributes.Attributes${'$'}PaddingAttribute#ADAPTER",
    jsonName = "layoutPadding",
    oneofName = "type",
    schemaIndex = 0,
  )
  public val layout_padding: PaddingAttribute? = null,
  @field:WireField(
    tag = 2,
    adapter = "protobuf.source.attributes.Attributes${'$'}SizeAttribute#ADAPTER",
    oneofName = "type",
    schemaIndex = 1,
  )
  public val size: SizeAttribute? = null,
  unknownFields: ByteString = ByteString.EMPTY,
) : Message<Attributes, Nothing>(ADAPTER, unknownFields) {
  init {
    require(countNonNull(layout_padding, size) <= 1) {
      "At most one of layout_padding, size may be non-null"
    }
  }

  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN,
  )
  override fun newBuilder(): Nothing = throw AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is Attributes) return false
    if (unknownFields != other.unknownFields) return false
    if (layout_padding != other.layout_padding) return false
    if (size != other.size) return false
    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + (layout_padding?.hashCode() ?: 0)
      result = result * 37 + (size?.hashCode() ?: 0)
      super.hashCode = result
    }
    return result
  }

  override fun toString(): String {
    val result = mutableListOf<String>()
    if (layout_padding != null) result += """layout_padding=$layout_padding"""
    if (size != null) result += """size=$size"""
    return result.joinToString(prefix = "Attributes{", separator = ", ", postfix = "}")
  }

  public fun copy(
    layout_padding: PaddingAttribute? = this.layout_padding,
    size: SizeAttribute? = this.size,
    unknownFields: ByteString = this.unknownFields,
  ): Attributes = Attributes(layout_padding, size, unknownFields)

  public companion object {
    @JvmField
    public val ADAPTER: ProtoAdapter<Attributes> = object : ProtoAdapter<Attributes>(
      FieldEncoding.LENGTH_DELIMITED,
      Attributes::class,
      "type.googleapis.com/protobuf.source.attributes.Attributes",
      PROTO_3,
      null,
      "attributes/attributes.proto"
    ) {
      override fun encodedSize(`value`: Attributes): Int {
        var size_ = value.unknownFields.size
        size_ += PaddingAttribute.ADAPTER.encodedSizeWithTag(1, value.layout_padding)
        size_ += SizeAttribute.ADAPTER.encodedSizeWithTag(2, value.size)
        return size_
      }

      override fun encode(writer: ProtoWriter, `value`: Attributes) {
        PaddingAttribute.ADAPTER.encodeWithTag(writer, 1, value.layout_padding)
        SizeAttribute.ADAPTER.encodeWithTag(writer, 2, value.size)
        writer.writeBytes(value.unknownFields)
      }

      override fun encode(writer: ReverseProtoWriter, `value`: Attributes) {
        writer.writeBytes(value.unknownFields)
        SizeAttribute.ADAPTER.encodeWithTag(writer, 2, value.size)
        PaddingAttribute.ADAPTER.encodeWithTag(writer, 1, value.layout_padding)
      }

      override fun decode(reader: ProtoReader): Attributes {
        var layout_padding: PaddingAttribute? = null
        var size: SizeAttribute? = null
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> layout_padding = PaddingAttribute.ADAPTER.decode(reader)
            2 -> size = SizeAttribute.ADAPTER.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return Attributes(
          layout_padding = layout_padding,
          size = size,
          unknownFields = unknownFields
        )
      }

      override fun redact(`value`: Attributes): Attributes = value.copy(
        layout_padding = value.layout_padding?.let(PaddingAttribute.ADAPTER::redact),
        size = value.size?.let(SizeAttribute.ADAPTER::redact),
        unknownFields = ByteString.EMPTY
      )
    }

    private const val serialVersionUID: Long = 0L
  }

  public enum class SizeArea(
    override val `value`: Int,
  ) : WireEnum {
    SIZE_AREA_UNSPECIFIED(0),
    SIZE_AREA_WIDTH(1),
    SIZE_AREA_HEIGHT(2),
    ;

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<SizeArea> = object : EnumAdapter<SizeArea>(
        SizeArea::class,
        PROTO_3,
        SizeArea.SIZE_AREA_UNSPECIFIED
      ) {
        override fun fromValue(`value`: Int): SizeArea? = SizeArea.fromValue(`value`)
      }

      @JvmStatic
      public fun fromValue(`value`: Int): SizeArea? = when (`value`) {
        0 -> SIZE_AREA_UNSPECIFIED
        1 -> SIZE_AREA_WIDTH
        2 -> SIZE_AREA_HEIGHT
        else -> null
      }
    }
  }

  public class PaddingAttribute(
    @field:WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      schemaIndex = 0,
    )
    public val top: Float? = null,
    @field:WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      schemaIndex = 1,
    )
    public val leading: Float? = null,
    @field:WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      schemaIndex = 2,
    )
    public val bottom: Float? = null,
    @field:WireField(
      tag = 4,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      schemaIndex = 3,
    )
    public val trailing: Float? = null,
    unknownFields: ByteString = ByteString.EMPTY,
  ) : Message<PaddingAttribute, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN,
    )
    override fun newBuilder(): Nothing = throw AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is PaddingAttribute) return false
      if (unknownFields != other.unknownFields) return false
      if (top != other.top) return false
      if (leading != other.leading) return false
      if (bottom != other.bottom) return false
      if (trailing != other.trailing) return false
      return true
    }

    override fun hashCode(): Int {
      var result = super.hashCode
      if (result == 0) {
        result = unknownFields.hashCode()
        result = result * 37 + (top?.hashCode() ?: 0)
        result = result * 37 + (leading?.hashCode() ?: 0)
        result = result * 37 + (bottom?.hashCode() ?: 0)
        result = result * 37 + (trailing?.hashCode() ?: 0)
        super.hashCode = result
      }
      return result
    }

    override fun toString(): String {
      val result = mutableListOf<String>()
      if (top != null) result += """top=$top"""
      if (leading != null) result += """leading=$leading"""
      if (bottom != null) result += """bottom=$bottom"""
      if (trailing != null) result += """trailing=$trailing"""
      return result.joinToString(prefix = "PaddingAttribute{", separator = ", ", postfix = "}")
    }

    public fun copy(
      top: Float? = this.top,
      leading: Float? = this.leading,
      bottom: Float? = this.bottom,
      trailing: Float? = this.trailing,
      unknownFields: ByteString = this.unknownFields,
    ): PaddingAttribute = PaddingAttribute(top, leading, bottom, trailing, unknownFields)

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<PaddingAttribute> = object : ProtoAdapter<PaddingAttribute>(
        FieldEncoding.LENGTH_DELIMITED,
        PaddingAttribute::class,
        "type.googleapis.com/protobuf.source.attributes.Attributes.PaddingAttribute",
        PROTO_3,
        null,
        "attributes/attributes.proto"
      ) {
        override fun encodedSize(`value`: PaddingAttribute): Int {
          var size = value.unknownFields.size
          size += ProtoAdapter.FLOAT.encodedSizeWithTag(1, value.top)
          size += ProtoAdapter.FLOAT.encodedSizeWithTag(2, value.leading)
          size += ProtoAdapter.FLOAT.encodedSizeWithTag(3, value.bottom)
          size += ProtoAdapter.FLOAT.encodedSizeWithTag(4, value.trailing)
          return size
        }

        override fun encode(writer: ProtoWriter, `value`: PaddingAttribute) {
          ProtoAdapter.FLOAT.encodeWithTag(writer, 1, value.top)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 2, value.leading)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 3, value.bottom)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 4, value.trailing)
          writer.writeBytes(value.unknownFields)
        }

        override fun encode(writer: ReverseProtoWriter, `value`: PaddingAttribute) {
          writer.writeBytes(value.unknownFields)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 4, value.trailing)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 3, value.bottom)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 2, value.leading)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 1, value.top)
        }

        override fun decode(reader: ProtoReader): PaddingAttribute {
          var top: Float? = null
          var leading: Float? = null
          var bottom: Float? = null
          var trailing: Float? = null
          val unknownFields = reader.forEachTag { tag ->
            when (tag) {
              1 -> top = ProtoAdapter.FLOAT.decode(reader)
              2 -> leading = ProtoAdapter.FLOAT.decode(reader)
              3 -> bottom = ProtoAdapter.FLOAT.decode(reader)
              4 -> trailing = ProtoAdapter.FLOAT.decode(reader)
              else -> reader.readUnknownField(tag)
            }
          }
          return PaddingAttribute(
            top = top,
            leading = leading,
            bottom = bottom,
            trailing = trailing,
            unknownFields = unknownFields
          )
        }

        override fun redact(`value`: PaddingAttribute): PaddingAttribute = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }

      private const val serialVersionUID: Long = 0L
    }
  }

  public class SizeAttribute(
    @field:WireField(
      tag = 1,
      adapter = "protobuf.source.attributes.Attributes${'$'}SizeArea#ADAPTER",
      label = WireField.Label.OMIT_IDENTITY,
      schemaIndex = 0,
    )
    public val area: SizeArea = SizeArea.SIZE_AREA_UNSPECIFIED,
    @field:WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      jsonName = "minValue",
      schemaIndex = 1,
    )
    public val min_value: Float? = null,
    @field:WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      label = WireField.Label.OMIT_IDENTITY,
      jsonName = "defaultValue",
      schemaIndex = 2,
    )
    public val default_value: Float = 0f,
    @field:WireField(
      tag = 4,
      adapter = "com.squareup.wire.ProtoAdapter#FLOAT",
      jsonName = "maxValue",
      schemaIndex = 3,
    )
    public val max_value: Float? = null,
    unknownFields: ByteString = ByteString.EMPTY,
  ) : Message<SizeAttribute, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN,
    )
    override fun newBuilder(): Nothing = throw AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is SizeAttribute) return false
      if (unknownFields != other.unknownFields) return false
      if (area != other.area) return false
      if (min_value != other.min_value) return false
      if (default_value != other.default_value) return false
      if (max_value != other.max_value) return false
      return true
    }

    override fun hashCode(): Int {
      var result = super.hashCode
      if (result == 0) {
        result = unknownFields.hashCode()
        result = result * 37 + area.hashCode()
        result = result * 37 + (min_value?.hashCode() ?: 0)
        result = result * 37 + default_value.hashCode()
        result = result * 37 + (max_value?.hashCode() ?: 0)
        super.hashCode = result
      }
      return result
    }

    override fun toString(): String {
      val result = mutableListOf<String>()
      result += """area=$area"""
      if (min_value != null) result += """min_value=$min_value"""
      result += """default_value=$default_value"""
      if (max_value != null) result += """max_value=$max_value"""
      return result.joinToString(prefix = "SizeAttribute{", separator = ", ", postfix = "}")
    }

    public fun copy(
      area: SizeArea = this.area,
      min_value: Float? = this.min_value,
      default_value: Float = this.default_value,
      max_value: Float? = this.max_value,
      unknownFields: ByteString = this.unknownFields,
    ): SizeAttribute = SizeAttribute(area, min_value, default_value, max_value, unknownFields)

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<SizeAttribute> = object : ProtoAdapter<SizeAttribute>(
        FieldEncoding.LENGTH_DELIMITED,
        SizeAttribute::class,
        "type.googleapis.com/protobuf.source.attributes.Attributes.SizeAttribute",
        PROTO_3,
        null,
        "attributes/attributes.proto"
      ) {
        override fun encodedSize(`value`: SizeAttribute): Int {
          var size = value.unknownFields.size
          if (value.area != protobuf.source.attributes.Attributes.SizeArea.SIZE_AREA_UNSPECIFIED) {
            size += SizeArea.ADAPTER.encodedSizeWithTag(1, value.area)
          }
          size += ProtoAdapter.FLOAT.encodedSizeWithTag(2, value.min_value)
          if (!value.default_value.equals(0f)) {
            size += ProtoAdapter.FLOAT.encodedSizeWithTag(3, value.default_value)
          }
          size += ProtoAdapter.FLOAT.encodedSizeWithTag(4, value.max_value)
          return size
        }

        override fun encode(writer: ProtoWriter, `value`: SizeAttribute) {
          if (value.area != protobuf.source.attributes.Attributes.SizeArea.SIZE_AREA_UNSPECIFIED) {
            SizeArea.ADAPTER.encodeWithTag(writer, 1, value.area)
          }
          ProtoAdapter.FLOAT.encodeWithTag(writer, 2, value.min_value)
          if (!value.default_value.equals(0f)) {
            ProtoAdapter.FLOAT.encodeWithTag(writer, 3, value.default_value)
          }
          ProtoAdapter.FLOAT.encodeWithTag(writer, 4, value.max_value)
          writer.writeBytes(value.unknownFields)
        }

        override fun encode(writer: ReverseProtoWriter, `value`: SizeAttribute) {
          writer.writeBytes(value.unknownFields)
          ProtoAdapter.FLOAT.encodeWithTag(writer, 4, value.max_value)
          if (!value.default_value.equals(0f)) {
            ProtoAdapter.FLOAT.encodeWithTag(writer, 3, value.default_value)
          }
          ProtoAdapter.FLOAT.encodeWithTag(writer, 2, value.min_value)
          if (value.area != protobuf.source.attributes.Attributes.SizeArea.SIZE_AREA_UNSPECIFIED) {
            SizeArea.ADAPTER.encodeWithTag(writer, 1, value.area)
          }
        }

        override fun decode(reader: ProtoReader): SizeAttribute {
          var area: SizeArea = SizeArea.SIZE_AREA_UNSPECIFIED
          var min_value: Float? = null
          var default_value: Float = 0f
          var max_value: Float? = null
          val unknownFields = reader.forEachTag { tag ->
            when (tag) {
              1 -> try {
                area = SizeArea.ADAPTER.decode(reader)
              } catch (e: ProtoAdapter.EnumConstantNotFoundException) {
                reader.addUnknownField(tag, FieldEncoding.VARINT, e.value.toLong())
              }
              2 -> min_value = ProtoAdapter.FLOAT.decode(reader)
              3 -> default_value = ProtoAdapter.FLOAT.decode(reader)
              4 -> max_value = ProtoAdapter.FLOAT.decode(reader)
              else -> reader.readUnknownField(tag)
            }
          }
          return SizeAttribute(
            area = area,
            min_value = min_value,
            default_value = default_value,
            max_value = max_value,
            unknownFields = unknownFields
          )
        }

        override fun redact(`value`: SizeAttribute): SizeAttribute = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }

      private const val serialVersionUID: Long = 0L
    }
  }
}
