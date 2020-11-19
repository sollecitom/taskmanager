package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider

class UserProxiesFactory(private val productsFactory: ProductsFactory, private val eventsStore: EventsStore<Event>) : UsersFactory {

    override suspend fun create(id: Id, time: TimeProvider): User = UserProxy(id, time, productsFactory, eventsStore::publish)
}