package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.AbstractEvent
import java.time.Instant

class TeamWasCreated(val team: Team, timestamp: Instant, id: Id = Id.create()) : AbstractEvent(id, timestamp) {

    override val actorId: Id by team::createdBy
}