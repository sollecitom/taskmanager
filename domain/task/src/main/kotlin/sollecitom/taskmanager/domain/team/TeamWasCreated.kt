package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event
import java.time.Instant

class TeamWasCreated(val team: Team, timestamp: Instant, id: Id = Id.create()) : Event(id, timestamp) {

    override val actorId: Id by team::createdBy
}