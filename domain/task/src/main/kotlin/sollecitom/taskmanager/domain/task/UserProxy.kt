package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider

internal class UserProxy(override val id: Id, private val time: TimeProvider, private val productsFactory: ProductsFactory, private val publishEvent: suspend (Event) -> Unit) : User {

    override suspend fun createTask(taskId: Id): Task = TaskInfo(taskId, id, getTime()).also { publishEvent(Event.TaskWasCreated(it)) }

    override suspend fun createProduct(productId: Id): Product = productsFactory.create(productId, id, getTime()).also { publishEvent(Event.ProductWasCreated(it)) }

    override suspend fun add(task: Task, container: TasksContainer) = container.addTask(task, id, getTime()).also { publishEvent(Event.TaskWasAddedToContainer(task, container)) }

    private fun getTime() = time.get()
}