package bff.ui

internal class ProtobufData(
  private val backing: MutableMap<Any, Any> = mutableMapOf(),
) : MutableMap<Any, Any> by backing {
  private var locked = false

  override fun put(key: Any, value: Any): Any? {
    if (locked) error("ProtobufData is locked and cannot be modified")
    return backing.put(key, value)
  }

  internal fun lock() {
    check(!locked) { "ProtobufData is already locked" }
    locked = true
  }
}
