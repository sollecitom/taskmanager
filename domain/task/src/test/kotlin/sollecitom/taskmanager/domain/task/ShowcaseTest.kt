package sollecitom.taskmanager.domain.task

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import com.indexlabs.commons.domain.time.TimeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.Instant.now

private class ShowcaseTest {

    @Test
    fun `a user can create a task`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }

        val task = user.createTask()

        assertThat(task.createdBy).isEqualTo(user.id)
        assertThat(task.createdAt).isEqualTo(timestamp)
    }

    @Test
    fun `a user can create a product`() = runBlocking {

        val timestamp = now()
        val user = newUser { timestamp }

        val product = user.createProduct()

        assertThat(product.createdBy).isEqualTo(user.id)
        assertThat(product.createdAt).isEqualTo(timestamp)
    }

    @Test
    fun `a user can add a task to the backlog of a product`() = runBlocking {

        val user = newUser()
        val product = user.createProduct()
        val task = user.createTask()

        user.add(task, product)

        assertThat(product.tasks.toSet()).contains(task)
    }

    private val usersFactory = UserProxiesFactory(InMemoryProductsFactory)

    private suspend fun newUser(id: Id = Id.create(), time: TimeProvider = DefaultTimeProvider): User = usersFactory.create(id, time)
}

interface UsersFactory {

    suspend fun create(id: Id, time: TimeProvider): User
}

class UserProxiesFactory(private val productsFactory: ProductsFactory) : UsersFactory {

    override suspend fun create(id: Id, time: TimeProvider): User = UserProxy(id, time, productsFactory)
}

class UserProxy(override val id: Id, private val time: TimeProvider, private val productsFactory: ProductsFactory) : User {

    override suspend fun createTask(taskId: Id): Task {

        val createdAt = time.get()
        return TaskInfo(taskId, id, createdAt)
    }

    override suspend fun createProduct(productId: Id): Product {

        val createdAt = time.get()
        return productsFactory.create(productId, id, createdAt)
    }

    override suspend fun add(task: Task, container: TasksContainer) {

        val addedAt = time.get()
        container.addTask(task, id, addedAt)
    }
}

interface ProductsFactory {

    suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Product
}

object InMemoryProductsFactory : ProductsFactory {

    override suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Product {

        return ProductInfo(id, createdBy, createdAt, newTasksContainer())
    }

    private fun newTasksContainer(): TasksContainer = InMemoryTasksContainer()
}

interface Task : Identifiable<Id> {

    val createdBy: Id
    val createdAt: Instant
}

interface Product : Identifiable<Id>, TasksContainer {

    val createdBy: Id
    val createdAt: Instant
}

class TaskInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant) : Task

class ProductInfo(override val id: Id, override val createdBy: Id, override val createdAt: Instant, private val tasksContainer: TasksContainer) : Product, TasksContainer by tasksContainer

interface User : Identifiable<Id> {

    suspend fun createTask(taskId: Id = Id.create()): Task

    suspend fun createProduct(productId: Id = Id.create()): Product

    suspend fun add(task: Task, container: TasksContainer)
}

interface TasksContainer {

    val tasks: Flow<Task>

    fun addTask(task: Task, addedBy: Id, addedAt: Instant)
}

class InMemoryTasksContainer : TasksContainer {

    private val storage = mutableSetOf<Task>()

    override val tasks: Flow<Task> get() = storage.asFlow()

    override fun addTask(task: Task, addedBy: Id, addedAt: Instant) {

        storage += task // here we should capture an event
    }
}

object DefaultTimeProvider : TimeProvider {

    override fun get(): Instant = now()
}
