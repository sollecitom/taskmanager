package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event

class TaskWasAddedToContainer(val task: Task, val container: TasksContainer, id: Id = Id.create()) : Event(id) {

    override val actorId: Id by task::createdBy
}