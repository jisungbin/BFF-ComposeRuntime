package protobuf.generator

import com.squareup.wire.WireLogger
import com.squareup.wire.schema.KotlinTarget
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.WireRun
import java.io.File
import okio.FileSystem

private val projectDir = File(System.getProperty("user.dir"))
internal val PROTO_SOURCE_PATH = Location.get(projectDir.resolve("protobuf/src/schema").path)

internal fun main() {
  WireRun(
    sourcePath = listOf(PROTO_SOURCE_PATH),
    targets = listOf(KotlinTarget(outDirectory = projectDir.resolve("protobuf/src/main/kotlin").path)),
  )
    .execute(FileSystem.SYSTEM, WireLogger.NONE)
}

