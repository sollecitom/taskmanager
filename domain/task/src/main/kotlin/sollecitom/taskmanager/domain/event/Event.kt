package sollecitom.taskmanager.domain.event

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable

abstract class Event(override val id: Id) : Identifiable<Id> {

    abstract val actorId: Id
}