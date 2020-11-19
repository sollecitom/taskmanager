package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class InMemoryMembersContainer : MembersContainer {

    private val _membersIds = mutableSetOf<Id>()
    override val membersIds: Flow<Id> get() = _membersIds.asFlow()

    override fun addMemberId(memberId: Id) {

        _membersIds += memberId
    }

    override fun removeMemberId(memberId: Id) {

        _membersIds -= memberId
    }
}