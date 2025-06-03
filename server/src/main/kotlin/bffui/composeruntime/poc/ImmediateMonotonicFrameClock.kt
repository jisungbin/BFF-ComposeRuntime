package bffui.composeruntime.poc

import androidx.compose.runtime.MonotonicFrameClock

object ImmediateMonotonicFrameClock : MonotonicFrameClock {
  override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R =
    onFrame(0L)
}