package sollecitom.taskmanager.domain.task

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withTimeout
import java.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinDuration

@OptIn(ExperimentalTime::class)
internal suspend fun <T> Deferred<T>.await(duration: Duration): T = withTimeout(duration.toKotlinDuration()) { await() }