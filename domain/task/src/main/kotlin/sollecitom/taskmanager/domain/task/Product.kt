package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import java.time.Instant

interface Product : Identifiable<Id>, TasksContainer {

    val createdBy: Id
    val createdAt: Instant
}