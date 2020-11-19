package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event
import java.time.Instant

class UserWasAddedToContainer(val userId: Id, val container: MembersContainer, override val actorId: Id, timestamp: Instant, id: Id = Id.create()) : Event(id, timestamp)