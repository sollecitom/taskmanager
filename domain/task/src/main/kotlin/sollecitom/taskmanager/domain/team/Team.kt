package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import java.time.Instant

interface Team : Identifiable<Id>, MembersContainer {

    val createdBy: Id
    val createdAt: Instant
}
