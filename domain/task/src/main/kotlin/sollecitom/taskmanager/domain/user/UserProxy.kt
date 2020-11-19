package sollecitom.taskmanager.domain.user

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider
import sollecitom.taskmanager.domain.event.Event
import sollecitom.taskmanager.domain.product.Product
import sollecitom.taskmanager.domain.product.ProductWasCreated
import sollecitom.taskmanager.domain.product.ProductsFactory
import sollecitom.taskmanager.domain.task.Task
import sollecitom.taskmanager.domain.task.TaskInfo
import sollecitom.taskmanager.domain.task.TaskWasAddedToContainer
import sollecitom.taskmanager.domain.task.TaskWasCreated
import sollecitom.taskmanager.domain.task.TaskWasRemovedFromContainer
import sollecitom.taskmanager.domain.task.TasksContainer

internal class UserProxy(override val id: Id, private val time: TimeProvider, private val productsFactory: ProductsFactory, private val publishEvent: suspend (Event) -> Unit) : User {

    override suspend fun createTask(taskId: Id): Task = TaskInfo(taskId, id, getTime()).also { publishEvent(TaskWasCreated(it, getTime())) }

    override suspend fun createProduct(productId: Id): Product = productsFactory.create(productId, id, getTime()).also { publishEvent(ProductWasCreated(it, getTime())) }

    override suspend fun add(task: Task, container: TasksContainer) = container.addTask(task).also { publishEvent(TaskWasAddedToContainer(task, container, getTime())) }

    override suspend fun remove(task: Task, container: TasksContainer) = container.removeTask(task).also { publishEvent(TaskWasRemovedFromContainer(task, container, getTime())) }

    private fun getTime() = time.get()
}