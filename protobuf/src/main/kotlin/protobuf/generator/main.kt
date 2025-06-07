package protobuf.generator

import com.squareup.wire.schema.Location
import java.io.File
import protobuf.generator.RuntimeGenerator.RUNTIME_GENERATED_PACKAGE
import protobuf.generator.SchemaGenerator.SCHEMA_GENERATED_PACKAGE

internal val projectDir = File(System.getProperty("user.dir"))
internal val generatedDir = projectDir.resolve("ui/src/main/kotlin")

internal val PROTO_SOURCE_PATH = Location.get(projectDir.resolve("protobuf/src/schema").path)

internal const val GENERATED_COMMENT =
  "본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.\n임의로 수정하지 마세요."

internal fun main() {
  cleanGeneratedDir()

  println("----- ProtobufKotlinGenerator -----")
  ProtobufKotlinGenerator.generate()
  println()
  println("----- SchemaGenerator -----")
  SchemaGenerator.generateAttributes()
  SchemaGenerator.generateUiNodes()
  println()
  println("----- RuntimeGenerator -----")
  RuntimeGenerator.generateAttributeResolver()
  RuntimeGenerator.generateModelBuilder()
}

private fun cleanGeneratedDir() {
  val protoDestination = projectDir.resolve("protobuf/src/main/kotlin/protobuf/source")
  protoDestination.deleteRecursively()

  val schemaDestination = generatedDir.resolve(SCHEMA_GENERATED_PACKAGE.replace('.', '/'))
  schemaDestination.deleteRecursively()

  val runtimeDestination = generatedDir.resolve(RUNTIME_GENERATED_PACKAGE.replace('.', '/'))
  runtimeDestination.deleteRecursively()
}
