package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event

class TaskWasCreated(val task: Task, id: Id = Id.create()) : Event(id) {

    override val actorId: Id by task::createdBy
}