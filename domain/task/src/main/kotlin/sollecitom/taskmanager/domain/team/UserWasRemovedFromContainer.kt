package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.AbstractEvent
import java.time.Instant

class UserWasRemovedFromContainer(val userId: Id, val container: MembersContainer, override val actorId: Id, timestamp: Instant, id: Id = Id.create()) : AbstractEvent(id, timestamp)