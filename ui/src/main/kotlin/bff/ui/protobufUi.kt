package bff.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.withRunningRecomposer
import bff.ui.schema.ScreenScope
import bff.ui.schema.ScreenScopeProvider
import bff.ui.schema.WidgetScope
import bff.ui.schema.WidgetScopeProvider
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.withContext
import protobuf.source.response.Response

internal enum class ResponseType {
  Screen,
  Widget,
}

public suspend fun screens(content: @Composable ScreenScope.() -> Unit): Response =
  protobufUi(ResponseType.Screen) { ScreenScopeProvider.content() }

public suspend fun widgets(content: @Composable WidgetScope.() -> Unit): Response =
  protobufUi(ResponseType.Widget) { WidgetScopeProvider.content() }

private suspend fun protobufUi(type: ResponseType, content: @Composable () -> Unit): Response {
  val runningJob = Job(parent = coroutineContext[Job])
  var composition: Composition? = null

  val uiRoot = ProtobufNode.Root(type)
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
        val result = runCatching { setContent(content) }
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
