package bff.ui

import androidx.compose.runtime.Updater
import androidx.compose.runtime.collection.mutableVectorOf
import bff.ui.runtime.ModelBuilder
import java.util.Collections
import kotlin.properties.Delegates
import protobuf.source.response.Response

internal open class ProtobufNode private constructor() {
  internal lateinit var id: String
  internal lateinit var attributes: Attributes
  internal lateinit var actions: Actions
  internal lateinit var scope: UiScope

  internal val data: ProtobufData = ProtobufData()
  internal var childIndex by Delegates.notNull<Int>()

  @Suppress("PropertyName")
  protected val _children = mutableVectorOf<ProtobufNode>()
  internal val children: List<ProtobufNode>
    get() = Collections.unmodifiableList(_children.asMutableList())

  internal fun insertAt(index: Int, node: ProtobufNode) {
    node.childIndex = index
    _children.add(index, node)
  }

  override fun toString(): String =
    if (::scope.isInitialized) "${scope.idString()}@${hashCode().toString(16)}" else super.toString()

  internal class Root : ProtobufNode() {
    init {
      id = "root"
      attributes = Attributes
      actions = Actions
      scope = UiScope.Root
    }

    internal fun response(): Response = ModelBuilder.buildResponse(this)

    internal fun clear() {
      _children.clear()
      data.clear()
    }
  }

  internal companion object {
    internal val CONSTRUCTOR = ::ProtobufNode
    internal val INIT: Updater<ProtobufNode>.(Attributes, Actions, UiScope, ProtobufApplier) -> Unit =
      { attributes, actions, scope, applier ->
        init {
          this.id = applier.calculateStackBasedId(scope, "child", childIndex)
          this.attributes = attributes
          this.actions = actions.initializeIds { _, index ->
            applier.calculateStackBasedId(scope, "action", index)
          }
          this.scope = scope
        }
      }
  }
}

private fun ProtobufApplier.calculateStackBasedId(
  scope: UiScope,
  prefix: String,
  index: Int,
): String {
  // stack.size == 1 은 stack=[root] 상태임
  val previousNode = if (stack.size == 1) null else stack.last()
  return "${previousNode?.id?.plus('_').orEmpty()}${scope.idString()}-$prefix$index"
}
