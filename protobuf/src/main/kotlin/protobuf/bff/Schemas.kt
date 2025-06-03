package protobuf.bff

import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.SchemaLoader
import okio.FileSystem
import protobuf.generator.PROTO_SOURCE_PATH

internal object Schemas {
  private val schema =
    SchemaLoader(FileSystem.SYSTEM)
      .apply { initRoots(listOf(PROTO_SOURCE_PATH)) }
      .loadSchema()

  internal val Screen: MessageType = loadSingleMessage("screen/screen.proto")
  internal val Section: MessageType = loadSingleMessage("section/section.proto")
  internal val Widget: MessageType = loadSingleMessage("widget/widget.proto")

  private fun loadSingleMessage(path: String): MessageType =
    schema.protoFile(path)!!.types.single() as MessageType
}