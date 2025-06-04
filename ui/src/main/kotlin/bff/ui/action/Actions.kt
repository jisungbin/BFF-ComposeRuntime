package bff.ui.action

import androidx.collection.MutableObjectList
import androidx.collection.ObjectList
import androidx.collection.mutableObjectListOf
import androidx.collection.objectListOf
import dev.drewhamilton.poko.Poko
import protobuf.source.action.Action as ActionSource

public sealed interface Actions {
  public val value: ObjectList<Action>

  public fun action(
    type: ActionSource.Type,
    triggerCondition: ActionSource.TriggerCondition,
    uriScheme: String? = null,
  ): Actions

  public companion object : Actions {
    override val value: ObjectList<Action> get() = objectListOf()

    override fun action(
      type: ActionSource.Type,
      triggerCondition: ActionSource.TriggerCondition,
      uriScheme: String?,
    ): Actions =
      MutableActions().apply { value.add(Action(type, triggerCondition, uriScheme)) }
  }
}

private class MutableActions : Actions {
  override val value: MutableObjectList<Action> = mutableObjectListOf()

  override fun action(
    type: ActionSource.Type,
    triggerCondition: ActionSource.TriggerCondition,
    uriScheme: String?,
  ): Actions =
    apply { value.add(Action(type, triggerCondition, uriScheme)) }
}

@Poko public class Action internal constructor(
  public val type: ActionSource.Type,
  public val triggerCondition: ActionSource.TriggerCondition,
  public val uriScheme: String? = null,
  internal var id: Int? = null,
)
