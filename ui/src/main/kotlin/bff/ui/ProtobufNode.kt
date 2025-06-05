package bff.ui

import androidx.collection.ObjectList
import androidx.collection.mutableObjectListOf
import androidx.compose.runtime.Updater
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.properties.Delegates

internal class ProtobufNode {
  internal lateinit var id: String
  internal lateinit var attributes: Attributes
  internal lateinit var actions: Actions
  internal lateinit var scope: UiScope

  internal val data: ProtobufData = ProtobufData()
  internal var compositeKeyHash by Delegates.notNull<Int>()

  private val _children = mutableObjectListOf<ProtobufNode>()
  internal val children: ObjectList<ProtobufNode> get() = _children

  internal fun insertAt(index: Int, node: ProtobufNode) {
    _children.add(index, node)
  }

  internal fun clear() {
    _children.clear()
  }

  internal fun buildModel(): Any = TODO()

  internal companion object {
    internal val CONSTRUCTOR = ::ProtobufNode
    internal val INIT: Updater<ProtobufNode>.(Attributes, Actions, UiScope, Int) -> Unit =
      { attributes, actions, scope, compositeKeyHash ->
        init {
          this.attributes = attributes
          this.actions = actions
          this.scope = scope
          this.compositeKeyHash = compositeKeyHash
        }
      }
  }
}
