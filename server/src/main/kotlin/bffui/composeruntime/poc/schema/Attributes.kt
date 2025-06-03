package bffui.composeruntime.poc.schema

import androidx.collection.IntObjectMap
import androidx.collection.MutableObjectList
import androidx.collection.ObjectList
import androidx.collection.mutableObjectListOf
import androidx.collection.objectListOf

sealed interface Attributes {
  val value: ObjectList<Attribute>

  companion object : Attributes {
    override val value: ObjectList<Attribute> get() = objectListOf()
  }
}

private class MutableAttributes : Attributes {
  override val value: MutableObjectList<Attribute> = mutableObjectListOf()
}

sealed interface Attribute {
  val name: Int
  val arguments: IntObjectMap<Any>
}

infix fun Attributes.then(other: Attribute): Attributes {
  val backing = this as? MutableAttributes ?: MutableAttributes()
  backing.value.add(other)
  return backing
}
