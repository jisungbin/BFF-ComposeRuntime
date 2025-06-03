package bffui.composeruntime.poc

import androidx.compose.runtime.AbstractApplier

class ProtobufApplier(root: ProtobufNode) : AbstractApplier<ProtobufNode>(root) {
  override fun insertTopDown(index: Int, instance: ProtobufNode) {
    current.insertAt(index, instance)
  }

  override fun up() {
    current.lock()
    super.up()
  }

  override fun insertBottomUp(index: Int, instance: ProtobufNode) {
    // Ignored. Use `insertTopDown` instead.
  }

  override fun move(from: Int, to: Int, count: Int) {
    throw NotImplementedError("Not implemented in POC")
  }

  override fun remove(index: Int, count: Int) {
    throw NotImplementedError("Not implemented in POC")
  }

  override fun onClear() {
    current.clear()
  }
}
