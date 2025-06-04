package protobuf.generator

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

internal sealed class GeneratedResult {
  internal data class Attribute(
    internal val attributeFunction: FunSpec,
  ) : GeneratedResult()

  internal data class UiNode(
    internal val scopeInterfaces: List<TypeSpec>,
    internal val scopeProviders: List<TypeSpec>,
    internal val nodeFunctions: List<FunSpec>,
  ) : GeneratedResult()
}