package bffui.composeruntime.poc

class ProtobufData(
  private val backing: MutableMap<String, Any> = mutableMapOf(),
) : MutableMap<String, Any> by backing {
  private var locked = false

  override fun put(key: String, value: Any): Any? {
    if (locked) error("ProtobufData is locked and cannot be modified")
    return backing.put(key, value)
  }

  fun lock() {
    check(!locked) { "ProtobufData is already locked" }
    locked = true
  }
}
