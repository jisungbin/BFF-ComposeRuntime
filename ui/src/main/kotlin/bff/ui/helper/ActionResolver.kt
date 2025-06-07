package bff.ui.helper

import bff.ui.Actions
import bff.ui.BffUiCodegenException
import protobuf.source.action.Action

internal object ActionResolver {
  internal fun resolve(actions: Actions): List<Action> {
    if (actions === Actions.Companion) return emptyList()

    return actions.value.fold(mutableListOf()) { acc, element ->
      val id = element.id ?: throw BffUiCodegenException("The Action's id has not been initialised.")
      acc.add(Action(id, element.type, element.triggerCondition, element.uriScheme))
      acc
    }
  }
}
