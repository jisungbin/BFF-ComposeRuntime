package protobuf.generator

import com.squareup.wire.WireLogger
import com.squareup.wire.schema.KotlinTarget
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.WireRun
import okio.FileSystem
import okio.Path

internal object ProtobufKotlinGenerator {
  internal fun generate() {
    WireRun(
      sourcePath = listOf(PROTO_SOURCE_PATH),
      targets = listOf(
        KotlinTarget(
          outDirectory = projectDir.resolve("protobuf/src/main/kotlin").path,
          escapeKotlinKeywords = true,
        )
      ),
    )
      .execute(FileSystem.SYSTEM, GeneratedLogger())
  }

  private class GeneratedLogger : WireLogger {
    override fun artifactHandled(outputPath: Path, qualifiedName: String, targetName: String) {
      val artifactName = qualifiedName.substringAfterLast('.')
      val fullOutputPath = outputPath.resolve(qualifiedName.replace('.', '/') + ".kt")
      println("Generated $artifactName in $fullOutputPath")
    }

    override fun artifactSkipped(type: ProtoType, targetName: String) = Unit
    override fun unusedRoots(unusedRoots: Set<String>) = Unit
    override fun unusedPrunes(unusedPrunes: Set<String>) = Unit
    override fun unusedIncludesInTarget(unusedIncludes: Set<String>) = Unit
    override fun unusedExcludesInTarget(unusedExcludes: Set<String>) = Unit
  }
}
