package bff.ui

import androidx.collection.MutableObjectList
import androidx.collection.ObjectList
import androidx.collection.mutableObjectListOf
import androidx.collection.objectListOf
import dev.drewhamilton.poko.Poko
import protobuf.source.action.Action as ProtoAction

public sealed interface Actions {
  public val value: ObjectList<Action>

  public fun action(
    type: ProtoAction.Type,
    triggerCondition: ProtoAction.TriggerCondition,
    uriScheme: String? = null,
  ): Actions

  public companion object : Actions {
    override val value: ObjectList<Action> get() = objectListOf()

    override fun action(
      type: ProtoAction.Type,
      triggerCondition: ProtoAction.TriggerCondition,
      uriScheme: String?,
    ): Actions =
      MutableActions().action(type, triggerCondition, uriScheme)
  }
}

private class MutableActions : Actions {
  override val value: MutableObjectList<Action> = mutableObjectListOf()

  override fun action(
    type: ProtoAction.Type,
    triggerCondition: ProtoAction.TriggerCondition,
    uriScheme: String?,
  ): Actions {
    require(type != ProtoAction.Type.TYPE_UNSPECIFIED) {
      "Action의 type은 UNSPECIFIED가 아닌 값을 사용해야 합니다."
    }
    require(triggerCondition != ProtoAction.TriggerCondition.TRIGGER_CONDITION_UNSPECIFIED) {
      "Action의 triggerCondition은 UNSPECIFIED가 아닌 값을 사용해야 합니다."
    }

    return apply { value.add(Action(type, triggerCondition, uriScheme)) }
  }
}

@Poko public class Action internal constructor(
  public val type: ProtoAction.Type,
  public val triggerCondition: ProtoAction.TriggerCondition,
  public val uriScheme: String? = null,
  @Poko.Skip internal var id: String? = null,
)

internal fun Actions.initializeIds(calculate: (Action, Int) -> String): Actions {
  if (this !is MutableActions) return this
  value.forEachIndexed { index, action ->
    action.id = calculate(action, index)
  }
  return this
}
