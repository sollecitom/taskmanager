package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

interface TeamsFactory {

    suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Team
}