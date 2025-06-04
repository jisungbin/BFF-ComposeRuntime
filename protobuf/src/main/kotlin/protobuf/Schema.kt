package protobuf

import com.squareup.wire.schema.EnumType
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.SchemaLoader
import com.squareup.wire.schema.Type
import okio.FileSystem
import protobuf.generator.PROTO_SOURCE_PATH

internal object Schema {
  private val schema =
    SchemaLoader(FileSystem.SYSTEM)
      .apply { initRoots(listOf(PROTO_SOURCE_PATH)) }
      .loadSchema()

  internal val Screen: MessageType = loadSingleMessage("screen/screen.proto")
  internal val Section: MessageType = loadSingleMessage("section/section.proto")
  internal val Widget: MessageType = loadSingleMessage("widget/widget.proto")
  internal val Attributes: MessageType = loadSingleMessage("attributes/attributes.proto")

  internal fun type(type: ProtoType): Type? = schema.getType(type)
  internal fun message(type: ProtoType): MessageType = type(type) as MessageType

  private fun loadSingleMessage(path: String): MessageType =
    schema.protoFile(path)!!.types.single() as MessageType
}