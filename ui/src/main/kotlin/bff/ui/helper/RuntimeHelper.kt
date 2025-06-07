package bff.ui.helper

import bff.ui.BffUiCodegenException
import bff.ui.ProtobufNode
import bff.ui.UiScope
import bff.ui.idString
import bff.ui.runtime.ActionResolver
import bff.ui.runtime.AttributeResolver
import protobuf.source.component.ComponentBase

// TODO(dx) 더 좋은 에러 메시지
internal fun ProtobufNode.childOfScope(scope: UiScope): ProtobufNode =
  childOfScopeOrNull(scope) ?: error("No child of scope ${scope.idString()} was provided.")

internal fun ProtobufNode.childOfScopeOrNull(scope: UiScope): ProtobufNode? =
  children.find { it.scope == scope }

internal fun ProtobufNode.childrenOfScope(scope: UiScope): List<ProtobufNode> =
  children.filter { it.scope == scope }

internal fun ProtobufNode.componentBase(): ComponentBase =
  ComponentBase(
    id = id,
    attributes = AttributeResolver.resolve(attributes),
    actions = ActionResolver.resolve(actions),
  )

internal inline fun <reified T : UiScope> checkScope(node: ProtobufNode): T =
  node.scope as? T
    ?: throw BffUiCodegenException("Expected scope is ${T::class.simpleName}, but was ${node.scope::class.simpleName}.")

internal inline fun <reified T> checkType(value: Any?): T {
  if (value !is T)
    throw BffUiCodegenException("Expected type is ${T::class.qualifiedName}, but was ${value?.javaClass?.canonicalName}.")

  return value
}

internal inline fun <reified T> checkTypeIfNotNull(value: Any?): T? {
  if (value == null) return null
  if (value !is T)
    throw BffUiCodegenException("Expected type is ${T::class.qualifiedName}, but was ${value::class.qualifiedName}.")

  return value
}

internal inline fun <reified T : UiScope> checkChildrenScope(node: ProtobufNode) {
  if (node.children.any { it.scope !is T })
    throw BffUiCodegenException(
      "Expected all children of node ${node.scope.idString()} to be of scope ${T::class.simpleName}, " +
        "but found some children with different scopes: ${node.children.map { it.scope::class.simpleName }}."
    )
}
