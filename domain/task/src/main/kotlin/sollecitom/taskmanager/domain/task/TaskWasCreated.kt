package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.AbstractEvent
import java.time.Instant

class TaskWasCreated(override val task: Task, timestamp: Instant, id: Id = Id.create()) : AbstractEvent(id, timestamp), TaskEvent {

    override val actorId: Id by task::createdBy
}