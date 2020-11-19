package sollecitom.taskmanager.domain.user

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider

interface UsersFactory {

    suspend fun create(id: Id, time: TimeProvider): User
}