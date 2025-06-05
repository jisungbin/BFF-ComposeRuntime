package protobuf.generator

import com.squareup.wire.schema.Location
import java.io.File
import protobuf.generator.UiGenerator.UI_GENERATED_PACKAGE

internal val projectDir = File(System.getProperty("user.dir"))
internal val PROTO_SOURCE_PATH = Location.get(projectDir.resolve("protobuf/src/schema").path)

internal const val GENERATED_COMMENT =
  "본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.\n임의로 수정하지 마세요."

internal fun main() {
  val protoDestination = projectDir.resolve("protobuf/src/main/kotlin/protobuf/source")
  protoDestination.deleteRecursively()

  val uiDestination = UiGenerator.GENERATED_DIR.resolve(UI_GENERATED_PACKAGE.replace('.', '/'))
  uiDestination.deleteRecursively()

  println("----- ProtobufKotlinGenerator -----")
  ProtobufKotlinGenerator.generate()
  println()
  println("----- UiGenerator -----")
  UiGenerator.generateAttributes()
  UiGenerator.generateUiNodes()
}
