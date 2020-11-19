package sollecitom.taskmanager.domain.user

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.time.TimeProvider
import sollecitom.taskmanager.domain.event.Event
import sollecitom.taskmanager.domain.product.Product
import sollecitom.taskmanager.domain.product.ProductWasCreated
import sollecitom.taskmanager.domain.product.ProductsFactory
import sollecitom.taskmanager.domain.task.Task
import sollecitom.taskmanager.domain.task.TaskWasAddedToContainer
import sollecitom.taskmanager.domain.task.TaskWasCreated
import sollecitom.taskmanager.domain.task.TaskWasRemovedFromContainer
import sollecitom.taskmanager.domain.task.TasksContainer
import sollecitom.taskmanager.domain.task.TasksFactory
import sollecitom.taskmanager.domain.team.Team
import sollecitom.taskmanager.domain.team.TeamWasCreated
import sollecitom.taskmanager.domain.team.TeamsFactory

// TODO should publish event be part of the factories instead?
internal class UserProxy(override val id: Id, private val time: TimeProvider, private val tasksFactory: TasksFactory, private val productsFactory: ProductsFactory, private val teamsFactory: TeamsFactory, private val publishEvent: suspend (Event) -> Unit) : User {

    override suspend fun createTask(taskId: Id): Task = tasksFactory.create(taskId, id, getTime()).also { publishEvent(TaskWasCreated(it, getTime())) }

    override suspend fun createProduct(productId: Id): Product = productsFactory.create(productId, id, getTime()).also { publishEvent(ProductWasCreated(it, getTime())) }

    override suspend fun add(task: Task, container: TasksContainer) = container.addTask(task).also { publishEvent(TaskWasAddedToContainer(task, container, getTime())) }

    override suspend fun remove(task: Task, container: TasksContainer) = container.removeTask(task).also { publishEvent(TaskWasRemovedFromContainer(task, container, getTime())) }

    override suspend fun createTeam(teamId: Id): Team = teamsFactory.create(teamId, id, getTime()).also { publishEvent(TeamWasCreated(it, getTime())) }

    private fun getTime() = time.get()
}