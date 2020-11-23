package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.Flow

interface MembersContainer {

    val membersIds: Flow<Id>

    suspend fun addMemberId(memberId: Id)

    suspend fun removeMemberId(memberId: Id)
}