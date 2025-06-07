// 본 파일은 protobuf.generator.MainKt로 자동 생성되었습니다.
// 임의로 수정하지 마세요.
package bff.ui.runtime

import bff.ui.Actions
import bff.ui.BffUiCodegenException
import kotlin.collections.List
import protobuf.source.action.Action

internal object ActionResolver {
  internal fun resolve(actions: Actions): List<Action> {
    if (actions === Actions) return emptyList()

    return actions.value.fold(mutableListOf()) { acc, element ->
      val id = element.id ?: throw BffUiCodegenException("The Action's id has not been initialised.")
      acc.add(Action(id, element.type, element.triggerCondition, element.uriScheme))
      acc
    }
  }
}
