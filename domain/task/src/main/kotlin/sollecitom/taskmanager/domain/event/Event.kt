package sollecitom.taskmanager.domain.event

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import java.time.Instant

abstract class Event(override val id: Id, val timestamp: Instant) : Identifiable<Id> {

    abstract val actorId: Id

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Event
        if (id != other.id) return false
        return true
    }

    final override fun hashCode() = id.hashCode()
}