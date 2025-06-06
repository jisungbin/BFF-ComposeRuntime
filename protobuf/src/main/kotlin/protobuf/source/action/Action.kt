// Code generated by Wire protocol buffer compiler, do not edit.
// Source: protobuf.source.action.Action in action/action.proto
@file:Suppress(
  "DEPRECATION",
  "RUNTIME_ANNOTATION_NOT_SUPPORTED",
)

package protobuf.source.action

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
import com.squareup.wire.internal.sanitize
import okio.ByteString

public class Action(
  @field:WireField(
    tag = 1,
    adapter = "com.squareup.wire.ProtoAdapter#STRING",
    label = WireField.Label.OMIT_IDENTITY,
    schemaIndex = 0,
  )
  public val id: String = "",
  @field:WireField(
    tag = 2,
    adapter = "protobuf.source.action.Action${'$'}Type#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY,
    schemaIndex = 1,
  )
  public val type: Type = Type.TYPE_UNSPECIFIED,
  @field:WireField(
    tag = 3,
    adapter = "protobuf.source.action.Action${'$'}TriggerCondition#ADAPTER",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "triggerCondition",
    schemaIndex = 2,
  )
  public val trigger_condition: TriggerCondition = TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED,
  @field:WireField(
    tag = 4,
    adapter = "com.squareup.wire.ProtoAdapter#STRING",
    jsonName = "uriScheme",
    schemaIndex = 3,
  )
  public val uri_scheme: String? = null,
  unknownFields: ByteString = ByteString.EMPTY,
) : Message<Action, Nothing>(ADAPTER, unknownFields) {
  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN,
  )
  override fun newBuilder(): Nothing = throw AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is Action) return false
    if (unknownFields != other.unknownFields) return false
    if (id != other.id) return false
    if (type != other.type) return false
    if (trigger_condition != other.trigger_condition) return false
    if (uri_scheme != other.uri_scheme) return false
    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + id.hashCode()
      result = result * 37 + type.hashCode()
      result = result * 37 + trigger_condition.hashCode()
      result = result * 37 + (uri_scheme?.hashCode() ?: 0)
      super.hashCode = result
    }
    return result
  }

  override fun toString(): String {
    val result = mutableListOf<String>()
    result += """id=${sanitize(id)}"""
    result += """type=$type"""
    result += """trigger_condition=$trigger_condition"""
    if (uri_scheme != null) result += """uri_scheme=${sanitize(uri_scheme)}"""
    return result.joinToString(prefix = "Action{", separator = ", ", postfix = "}")
  }

  public fun copy(
    id: String = this.id,
    type: Type = this.type,
    trigger_condition: TriggerCondition = this.trigger_condition,
    uri_scheme: String? = this.uri_scheme,
    unknownFields: ByteString = this.unknownFields,
  ): Action = Action(id, type, trigger_condition, uri_scheme, unknownFields)

  public companion object {
    @JvmField
    public val ADAPTER: ProtoAdapter<Action> = object : ProtoAdapter<Action>(
      FieldEncoding.LENGTH_DELIMITED,
      Action::class,
      "type.googleapis.com/protobuf.source.action.Action",
      PROTO_3,
      null,
      "action/action.proto"
    ) {
      override fun encodedSize(`value`: Action): Int {
        var size = value.unknownFields.size
        if (value.id != "") {
          size += ProtoAdapter.STRING.encodedSizeWithTag(1, value.id)
        }
        if (value.type != protobuf.source.action.Action.Type.TYPE_UNSPECIFIED) {
          size += Type.ADAPTER.encodedSizeWithTag(2, value.type)
        }
        if (value.trigger_condition != protobuf.source.action.Action.TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED) {
          size += TriggerCondition.ADAPTER.encodedSizeWithTag(3, value.trigger_condition)
        }
        size += ProtoAdapter.STRING.encodedSizeWithTag(4, value.uri_scheme)
        return size
      }

      override fun encode(writer: ProtoWriter, `value`: Action) {
        if (value.id != "") {
          ProtoAdapter.STRING.encodeWithTag(writer, 1, value.id)
        }
        if (value.type != protobuf.source.action.Action.Type.TYPE_UNSPECIFIED) {
          Type.ADAPTER.encodeWithTag(writer, 2, value.type)
        }
        if (value.trigger_condition != protobuf.source.action.Action.TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED) {
          TriggerCondition.ADAPTER.encodeWithTag(writer, 3, value.trigger_condition)
        }
        ProtoAdapter.STRING.encodeWithTag(writer, 4, value.uri_scheme)
        writer.writeBytes(value.unknownFields)
      }

      override fun encode(writer: ReverseProtoWriter, `value`: Action) {
        writer.writeBytes(value.unknownFields)
        ProtoAdapter.STRING.encodeWithTag(writer, 4, value.uri_scheme)
        if (value.trigger_condition != protobuf.source.action.Action.TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED) {
          TriggerCondition.ADAPTER.encodeWithTag(writer, 3, value.trigger_condition)
        }
        if (value.type != protobuf.source.action.Action.Type.TYPE_UNSPECIFIED) {
          Type.ADAPTER.encodeWithTag(writer, 2, value.type)
        }
        if (value.id != "") {
          ProtoAdapter.STRING.encodeWithTag(writer, 1, value.id)
        }
      }

      override fun decode(reader: ProtoReader): Action {
        var id: String = ""
        var type: Type = Type.TYPE_UNSPECIFIED
        var trigger_condition: TriggerCondition = TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED
        var uri_scheme: String? = null
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> id = ProtoAdapter.STRING.decode(reader)
            2 -> try {
              type = Type.ADAPTER.decode(reader)
            } catch (e: ProtoAdapter.EnumConstantNotFoundException) {
              reader.addUnknownField(tag, FieldEncoding.VARINT, e.value.toLong())
            }
            3 -> try {
              trigger_condition = TriggerCondition.ADAPTER.decode(reader)
            } catch (e: ProtoAdapter.EnumConstantNotFoundException) {
              reader.addUnknownField(tag, FieldEncoding.VARINT, e.value.toLong())
            }
            4 -> uri_scheme = ProtoAdapter.STRING.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return Action(
          id = id,
          type = type,
          trigger_condition = trigger_condition,
          uri_scheme = uri_scheme,
          unknownFields = unknownFields
        )
      }

      override fun redact(`value`: Action): Action = value.copy(
        unknownFields = ByteString.EMPTY
      )
    }

    private const val serialVersionUID: Long = 0L
  }

  public enum class Type(
    override val `value`: Int,
  ) : WireEnum {
    TYPE_UNSPECIFIED(0),
    TYPE_URI_SCHEME(1),
    TYPE_LOGGING(2),
    ;

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<Type> = object : EnumAdapter<Type>(
        Type::class,
        PROTO_3,
        Type.TYPE_UNSPECIFIED
      ) {
        override fun fromValue(`value`: Int): Type? = Type.fromValue(`value`)
      }

      @JvmStatic
      public fun fromValue(`value`: Int): Type? = when (`value`) {
        0 -> TYPE_UNSPECIFIED
        1 -> TYPE_URI_SCHEME
        2 -> TYPE_LOGGING
        else -> null
      }
    }
  }

  public enum class TriggerCondition(
    override val `value`: Int,
  ) : WireEnum {
    TRIGGER_CONDITION_UNSPECIFIED(0),
    TRIGGER_CONDITION_TAP(1),
    TRIGGER_CONDITION_IMMEDIATE(2),
    TRIGGER_CONDITION_EXIT_VIEW_ONCE(3),
    ;

    public companion object {
      @JvmField
      public val ADAPTER: ProtoAdapter<TriggerCondition> = object : EnumAdapter<TriggerCondition>(
        TriggerCondition::class,
        PROTO_3,
        TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED
      ) {
        override fun fromValue(`value`: Int): TriggerCondition? = TriggerCondition.fromValue(`value`)
      }

      @JvmStatic
      public fun fromValue(`value`: Int): TriggerCondition? = when (`value`) {
        0 -> TRIGGER_CONDITION_UNSPECIFIED
        1 -> TRIGGER_CONDITION_TAP
        2 -> TRIGGER_CONDITION_IMMEDIATE
        3 -> TRIGGER_CONDITION_EXIT_VIEW_ONCE
        else -> null
      }
    }
  }
}
