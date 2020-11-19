package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

internal class ProductInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant, private val tasksContainer: TasksContainer) : Product, TasksContainer by tasksContainer