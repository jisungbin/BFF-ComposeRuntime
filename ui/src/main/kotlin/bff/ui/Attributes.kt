package bff.ui

import androidx.collection.IntObjectMap
import androidx.collection.MutableObjectList
import androidx.collection.ObjectList
import androidx.collection.mutableObjectListOf
import androidx.collection.objectListOf
import dev.drewhamilton.poko.Poko

public sealed interface Attributes {
  public val value: ObjectList<Attribute>

  public companion object : Attributes {
    override val value: ObjectList<Attribute> get() = objectListOf()
  }
}

private class MutableAttributes : Attributes {
  override val value: MutableObjectList<Attribute> = mutableObjectListOf()
}

@Poko public class Attribute internal constructor(
  public val index: Int,
  public val arguments: IntObjectMap<Any>,
)

public infix fun Attributes.then(other: Attribute): Attributes {
  val backing = this as? MutableAttributes ?: MutableAttributes()
  backing.value.add(other)
  return backing
}
