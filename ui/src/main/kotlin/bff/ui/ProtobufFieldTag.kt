package bff.ui

@JvmInline internal value class ProtobufFieldTag(internal val tag: Int)

internal fun ProtobufNode.protoField(tag: Int, require: Boolean = false): Any? =
  data.firstNotNullOfOrNull { (key, value) ->
    if (key is ProtobufFieldTag && key.tag == tag) value
    else null
  }
    .also {
      if (require && it == null)
        throw BffUiCodegenException("Required field with tag $tag not found. (scope=$scope)")
    }
