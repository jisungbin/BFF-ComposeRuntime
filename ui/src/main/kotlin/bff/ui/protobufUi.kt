package bff.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.withRunningRecomposer
import bff.ui.schema.ProtobufUiScope
import bff.ui.schema.ProtobufUiScopeProvider
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.withContext

public suspend fun protobufUi(content: @Composable ProtobufUiScope.() -> Unit): String {
  val runningJob = Job(parent = coroutineContext[Job])

  val protobuf = ProtobufNode()
  var composition: Composition? = null

  withContext(coroutineContext + runningJob + ImmediateMonotonicFrameClock) {
    withRunningRecomposer { recomposer ->
      composition = Composition(ProtobufApplier(protobuf), recomposer).apply {
        setContent { ProtobufUiScopeProvider.content() }
      }
    }
  }

  runningJob.cancelAndJoin()
  return protobuf.buildModel().toString().also { composition?.dispose() }
}
