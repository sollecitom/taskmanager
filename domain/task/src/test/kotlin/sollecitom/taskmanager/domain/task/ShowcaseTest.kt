package sollecitom.taskmanager.domain.task

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import sollecitom.taskmanager.domain.task.Event.ProductWasCreated
import sollecitom.taskmanager.domain.task.Event.TaskWasAddedToContainer
import sollecitom.taskmanager.domain.task.Event.TaskWasCreated
import java.time.Duration
import java.time.Instant.now

@ExperimentalCoroutinesApi
private class ShowcaseTest {

    @Test
    fun `a user can create a task`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<TaskWasCreated>().first() }

        val task = user.createTask()

        assertThat(task.createdBy).isEqualTo(user.id)
        assertThat(task.createdAt).isEqualTo(timestamp)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.task).isEqualTo(task)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
        }
    }

    @Test
    fun `a user can create a product`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<ProductWasCreated>().first() }

        val product = user.createProduct()

        assertThat(product.createdBy).isEqualTo(user.id)
        assertThat(product.createdAt).isEqualTo(timestamp)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.product).isEqualTo(product)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
        }
    }

    @Test
    fun `a user can add a task to the backlog of a product`() = runBlocking {

        val user = newUser()
        val product = user.createProduct()
        val task = user.createTask()
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<TaskWasAddedToContainer>().first() }

        user.add(task, product)

        assertThat(product.tasks.toSet()).contains(task)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.task).isEqualTo(task)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
            assertThat(publishedEvent.container).isEqualTo(product)
        }
    }

    @Test
    fun `a task can only appear in the backlog of a product once`() = runBlocking {

        val user = newUser()
        val product = user.createProduct()
        val task = user.createTask()

        user.add(task, product)

        assertThat(product.tasks.toSet()).contains(task)
    }

    private val timeout = Duration.ofSeconds(5)
    private val eventStore = InMemoryEventsStore<Event>()
    private val events = eventStore.events
    private val usersFactory = UserProxiesFactory(InMemoryProductsFactory(), eventStore)

    private suspend fun newUser(id: Id = Id.create(), time: TimeProvider = DefaultTimeProvider): User = usersFactory.create(id, time)
}