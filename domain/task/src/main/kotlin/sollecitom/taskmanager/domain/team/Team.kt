package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface Team : Identifiable<Id> {

    val membersIds: Flow<Id>
    val createdBy: Id
    val createdAt: Instant
}
