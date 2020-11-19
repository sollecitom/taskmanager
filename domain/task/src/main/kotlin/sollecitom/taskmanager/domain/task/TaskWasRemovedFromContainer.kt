package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event
import java.time.Instant

class TaskWasRemovedFromContainer(val task: Task, val container: TasksContainer, timestamp: Instant, id: Id = Id.create()) : Event(id, timestamp) {

    override val actorId: Id by task::createdBy
}