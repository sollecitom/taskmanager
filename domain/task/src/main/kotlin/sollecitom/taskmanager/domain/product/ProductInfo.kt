package sollecitom.taskmanager.domain.product

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.task.TasksContainer
import java.time.Instant

internal class ProductInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant, private val tasksContainer: TasksContainer) : Product, TasksContainer by tasksContainer {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ProductInfo
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()
}