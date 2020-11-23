package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.AbstractEvent
import java.time.Instant

class TaskWasAddedToContainer(override val task: Task, val container: TasksContainer, timestamp: Instant, id: Id = Id.create()) : AbstractEvent(id, timestamp), TaskEvent {

    override val actorId: Id by task::createdBy
}