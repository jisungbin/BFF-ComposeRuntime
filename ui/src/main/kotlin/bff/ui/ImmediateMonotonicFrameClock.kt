package bff.ui

import androidx.compose.runtime.MonotonicFrameClock

internal object ImmediateMonotonicFrameClock : MonotonicFrameClock {
  override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R =
    onFrame(0L)
}