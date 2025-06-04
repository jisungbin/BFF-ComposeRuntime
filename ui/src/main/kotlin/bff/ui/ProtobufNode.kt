package bff.ui

import androidx.compose.runtime.Updater
import androidx.compose.runtime.collection.mutableVectorOf
import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import kotlin.properties.Delegates

public class ProtobufNode {
  public lateinit var id: String internal set
  public lateinit var attributes: Attributes internal set
  public lateinit var actions: Actions internal set
  public lateinit var scope: UiScope internal set

  public val data: ProtobufData = ProtobufData()
  internal var compositeKeyHash by Delegates.notNull<Int>()

  private val _children = mutableVectorOf<ProtobufNode>()
  public val children: List<ProtobufNode> get() = _children.asMutableList()

  public fun insertAt(index: Int, node: ProtobufNode) {
    _children.add(index, node)
  }

  public fun clear() {
    _children.clear()
  }

  public fun buildJson(): String = TODO()

  public companion object {
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
