package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

class InMemoryTeamsFactory : TeamsFactory {

    override suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Team = TeamInfo(id, createdBy, createdAt, newMembersContainer())

    private fun newMembersContainer(): MembersContainer = InMemoryMembersContainer()
}