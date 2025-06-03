package bffui.composeruntime.poc.schema

import dev.drewhamilton.poko.Poko
import protobuf.source.action.Action

@Poko class Action(
  val type: Action.Type,
  val triggerCondition: Action.TriggerCondition,
  val uriScheme: String? = null,
)
