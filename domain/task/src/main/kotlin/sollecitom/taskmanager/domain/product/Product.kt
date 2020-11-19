package sollecitom.taskmanager.domain.product

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import sollecitom.taskmanager.domain.task.TasksContainer
import java.time.Instant

interface Product : Identifiable<Id>, TasksContainer {

    val createdBy: Id
    val createdAt: Instant
}