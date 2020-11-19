package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

internal class TaskInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant) : Task {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskInfo
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()
}