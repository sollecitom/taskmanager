package sollecitom.taskmanager.domain.task

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsOnly
import assertk.assertions.doesNotContain
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
import sollecitom.taskmanager.domain.common.time.DefaultTimeProvider
import sollecitom.taskmanager.domain.event.Event
import sollecitom.taskmanager.domain.event.InMemoryEventsStore
import sollecitom.taskmanager.domain.product.InMemoryProductsFactory
import sollecitom.taskmanager.domain.product.ProductWasCreated
import sollecitom.taskmanager.domain.team.InMemoryTeamsFactory
import sollecitom.taskmanager.domain.team.TeamWasCreated
import sollecitom.taskmanager.domain.team.UserWasAddedToContainer
import sollecitom.taskmanager.domain.team.UserWasRemovedFromContainer
import sollecitom.taskmanager.domain.user.User
import sollecitom.taskmanager.domain.user.UserProxiesFactory
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
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
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
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
        }
    }

    @Test
    fun `a user can add a task to the backlog of a product`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val product = user.createProduct()
        val task = user.createTask()
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<TaskWasAddedToContainer>().first() }

        user.add(task, product)

        assertThat(product.tasks.toSet()).contains(task)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.task).isEqualTo(task)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
            assertThat(publishedEvent.container).isEqualTo(product)
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
        }
    }

    @Test
    fun `a user can remove a task from the backlog of a product`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val product = user.createProduct()
        val task = user.createTask()
        user.add(task, product)
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<TaskWasRemovedFromContainer>().first() }

        user.remove(task, product)

        assertThat(product.tasks.toSet()).doesNotContain(task)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.task).isEqualTo(task)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
            assertThat(publishedEvent.container).isEqualTo(product)
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
        }
    }

    @Test
    fun `a user can create a team`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<TeamWasCreated>().first() }

        val team = user.createTeam()

        assertThat(team.createdBy).isEqualTo(user.id)
        assertThat(team.createdAt).isEqualTo(timestamp)
        assertThat(team.membersIds.toSet()).containsOnly(user.id)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.team).isEqualTo(team)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
        }
    }

    @Test
    fun `a user can add another user to a team`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val anotherUser = newUser { timestamp }
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<UserWasAddedToContainer>().first() }
        val team = user.createTeam()

        user.add(anotherUser, team)

        assertThat(team.membersIds.toSet()).containsOnly(user.id, anotherUser.id)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.userId).isEqualTo(anotherUser.id)
            assertThat(publishedEvent.container).isEqualTo(team)
            assertThat(publishedEvent.actorId).isEqualTo(user.id)
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
        }
    }

    @Test
    fun `a user can remove another user from a team`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }
        val anotherUser = newUser { timestamp }
        val eventIsPublished = async(start = CoroutineStart.UNDISPATCHED) { events.filterIsInstance<UserWasRemovedFromContainer>().first() }
        val team = user.createTeam()
        user.add(anotherUser, team)

        anotherUser.remove(user, team)

        assertThat(team.membersIds.toSet()).containsOnly(anotherUser.id)
        eventIsPublished.await(timeout).let { publishedEvent ->
            assertThat(publishedEvent.userId).isEqualTo(user.id)
            assertThat(publishedEvent.container).isEqualTo(team)
            assertThat(publishedEvent.actorId).isEqualTo(anotherUser.id)
            assertThat(publishedEvent.timestamp).isEqualTo(timestamp)
        }
    }

    private val timeout = Duration.ofSeconds(5)
    private val eventStore = InMemoryEventsStore<Event>()
    private val events = eventStore.events
    private val usersFactory = UserProxiesFactory(InMemoryTasksFactory(), InMemoryProductsFactory(), InMemoryTeamsFactory(), eventStore)

    private suspend fun newUser(id: Id = Id.create(), time: TimeProvider = DefaultTimeProvider): User = usersFactory.create(id, time)
}