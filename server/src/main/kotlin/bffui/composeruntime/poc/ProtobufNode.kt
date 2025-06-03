package bffui.composeruntime.poc

import androidx.compose.runtime.collection.mutableVectorOf

class ProtobufNode {
  lateinit var kind: Kind
  val data = ProtobufData()

  private val _children = mutableVectorOf<ProtobufNode>()
  val children: List<ProtobufNode> get() = _children.asMutableList()

  fun insertAt(index: Int, node: ProtobufNode) {
    _children.add(index, node)
  }

  fun clear() {
    _children.clear()
  }

  fun lock() {
    data.lock()
  }

  fun buildJson(): String = TODO()
}
