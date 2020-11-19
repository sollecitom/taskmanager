package sollecitom.taskmanager.domain.user

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider
import sollecitom.taskmanager.domain.event.Event
import sollecitom.taskmanager.domain.event.EventsStore
import sollecitom.taskmanager.domain.product.ProductsFactory

class UserProxiesFactory(private val productsFactory: ProductsFactory, private val eventsStore: EventsStore<Event>) : UsersFactory {

    override suspend fun create(id: Id, time: TimeProvider): User = UserProxy(id, time, productsFactory, eventsStore::publish)
}