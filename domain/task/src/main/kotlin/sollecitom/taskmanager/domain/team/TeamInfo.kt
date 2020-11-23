package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.count
import java.time.Instant

internal class TeamInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant, private val membersContainer: MembersContainer) : Team, MembersContainer by membersContainer {

    override suspend fun removeMemberId(memberId: Id) {

        if (membersContainer.membersIds.count() == 1) {
            error("Cannot remove last member of a team")
        }
        membersContainer.removeMemberId(memberId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TeamInfo
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()
}