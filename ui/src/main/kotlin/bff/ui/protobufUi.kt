package bff.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.withRunningRecomposer
import bff.ui.schema.ProtobufUiScope
import bff.ui.schema.ProtobufUiScopeProvider
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.withContext
import protobuf.source.response.Response

public suspend fun protobufScreen(content: @Composable ProtobufUiScope.() -> Unit): Response {
  val runningJob = Job(parent = coroutineContext[Job])
  var composition: Composition? = null

  val uiRoot = ProtobufNode.Root()
  var uiException: Throwable? = null

  fun setUiException(throwable: Throwable?) {
    if (uiException == null && throwable != null) {
      uiException = throwable
    }
  }

  withContext(
    (coroutineContext + runningJob + ImmediateMonotonicFrameClock)
      .plus(CoroutineExceptionHandler { _, throwable -> setUiException(throwable) }),
  ) {
    withRunningRecomposer { recomposer ->
      composition = Composition(ProtobufApplier(uiRoot), recomposer).apply {
        val result = runCatching { setContent { ProtobufUiScopeProvider.content() } }
        setUiException(result.exceptionOrNull())
      }
    }
  }
  runningJob.cancelAndJoin()

  val uiResponse = runCatching(uiRoot::response).onFailure(::setUiException).getOrNull()
  composition?.dispose()

  return when {
    uiException != null -> Response(error = Response.Error(message = uiException.message ?: "Unknown error"))
    else -> uiResponse!!
  }
}
