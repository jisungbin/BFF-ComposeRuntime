package bffui.composeruntime.poc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.withRunningRecomposer
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
  val bffUiJson = runBlocking {
    protobuf {

    }
  }
  println(bffUiJson)
}

private suspend fun protobuf(content: @Composable () -> Unit): String {
  val runningJob = Job(parent = coroutineContext[Job])

  val protobuf = ProtobufNode()
  var composition: Composition? = null

  withContext(coroutineContext + runningJob + ImmediateMonotonicFrameClock) {
    withRunningRecomposer { recomposer ->
      composition = Composition(ProtobufApplier(protobuf), recomposer).apply {
        setContent(content)
      }
    }
  }

  runningJob.cancelAndJoin()
  return protobuf.buildJson().also { composition?.dispose() }
}
