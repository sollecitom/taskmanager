package sollecitom.taskmanager.domain.team

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant

internal class TeamInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant) : Team {

    override val membersIds: Flow<Id> = flowOf(createdBy) // TODO fix

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TeamInfo
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()
}