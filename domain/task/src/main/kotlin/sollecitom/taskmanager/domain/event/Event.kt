package sollecitom.taskmanager.domain.event

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import java.time.Instant

interface Event : Identifiable<Id> {

    val timestamp: Instant
    val actorId: Id
}

abstract class AbstractEvent(override val id: Id, override val timestamp: Instant) : Event {

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Event
        if (id != other.id) return false
        return true
    }

    final override fun hashCode() = id.hashCode()
}