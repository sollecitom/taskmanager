package sollecitom.taskmanager.domain.event

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import java.time.Instant

abstract class Event(override val id: Id, val timestamp: Instant) : Identifiable<Id> {

    abstract val actorId: Id
}