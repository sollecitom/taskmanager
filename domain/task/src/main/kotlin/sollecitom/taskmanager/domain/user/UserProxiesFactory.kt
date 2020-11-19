package sollecitom.taskmanager.domain.user

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider
import sollecitom.taskmanager.domain.event.Event
import sollecitom.taskmanager.domain.event.EventsStore
import sollecitom.taskmanager.domain.product.ProductsFactory
import sollecitom.taskmanager.domain.task.TasksFactory
import sollecitom.taskmanager.domain.team.TeamsFactory

class UserProxiesFactory(private val tasksFactory: TasksFactory, private val productsFactory: ProductsFactory, private val teamsFactory: TeamsFactory, private val eventsStore: EventsStore<Event>) : UsersFactory {

    override suspend fun create(id: Id, time: TimeProvider): User = UserProxy(id, time, tasksFactory, productsFactory, teamsFactory, eventsStore::publish)
}