package bff.ui

import androidx.compose.runtime.Applier
import androidx.compose.runtime.collection.mutableVectorOf
import java.util.Collections

// AbstractApplier은 stack이 private라 사용하지 않음 (고유한 id 계산에 stack 필요)
internal class ProtobufApplier(private val root: ProtobufNode.Root) : Applier<ProtobufNode> {
  private val _stack = mutableVectorOf<ProtobufNode>()
  internal val stack: List<ProtobufNode>
    get() = Collections.unmodifiableList(_stack.asMutableList())

  override var current: ProtobufNode = root
    private set

  override fun down(node: ProtobufNode) {
    _stack.add(current)
    current = node
  }

  override fun up() {
    current.data.lock()
    current = _stack.removeAt(_stack.lastIndex)
  }

  override fun insertTopDown(index: Int, instance: ProtobufNode) {
    current.insertAt(index, instance)
  }

  override fun insertBottomUp(index: Int, instance: ProtobufNode) {
    // Ignored. Use `insertTopDown` instead.
  }

  override fun move(from: Int, to: Int, count: Int): Nothing =
    throw NotImplementedError("Should not be called from the BFF UI")

  override fun remove(index: Int, count: Int): Nothing =
    throw NotImplementedError("Should not be called from the BFF UI")

  override fun clear() {
    _stack.clear()
    current = root.apply { clear() }
  }
}
