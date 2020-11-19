package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

internal class TaskInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant) : Task