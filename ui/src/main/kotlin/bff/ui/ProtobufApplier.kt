package bff.ui

import androidx.compose.runtime.AbstractApplier

internal class ProtobufApplier(root: ProtobufNode) : AbstractApplier<ProtobufNode>(root) {
  override fun insertTopDown(index: Int, instance: ProtobufNode) {
    current.insertAt(index, instance)
  }

  override fun up() {
    current.data.lock()
    super.up()
  }

  override fun insertBottomUp(index: Int, instance: ProtobufNode) {
    // Ignored. Use `insertTopDown` instead.
  }

  override fun move(from: Int, to: Int, count: Int): Nothing =
    throw NotImplementedError("Should not be called from the BFF UI")

  override fun remove(index: Int, count: Int): Nothing =
    throw NotImplementedError("Should not be called from the BFF UI")

  override fun onClear() {
    current.clear()
  }
}
