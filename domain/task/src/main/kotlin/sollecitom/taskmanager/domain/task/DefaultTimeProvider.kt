package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.time.TimeProvider
import java.time.Instant

object DefaultTimeProvider : TimeProvider {

    override fun get(): Instant = Instant.now()
}