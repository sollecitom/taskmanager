package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable

sealed class Event(override val id: Id) : Identifiable<Id> {

    abstract val actorId: Id

    class TaskWasCreated(val task: Task, id: Id = Id.create()) : Event(id) {

        override val actorId: Id by task::createdBy
    }

    class TaskWasAddedToContainer(val task: Task, val container: TasksContainer, id: Id = Id.create()) : Event(id) {

        override val actorId: Id by task::createdBy
    }

    class ProductWasCreated(val product: Product, id: Id = Id.create()) : Event(id) {

        override val actorId: Id by product::createdBy
    }
}