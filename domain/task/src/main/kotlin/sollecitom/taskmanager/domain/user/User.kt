package sollecitom.taskmanager.domain.user

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import sollecitom.taskmanager.domain.product.Product
import sollecitom.taskmanager.domain.task.Task
import sollecitom.taskmanager.domain.task.TasksContainer
import sollecitom.taskmanager.domain.team.Team

interface User : Identifiable<Id> {

    suspend fun createTask(taskId: Id = Id.create()): Task

    suspend fun createProduct(productId: Id = Id.create()): Product

    suspend fun add(task: Task, container: TasksContainer)

    suspend fun remove(task: Task, container: TasksContainer)

    suspend fun createTeam(teamId: Id = Id.create()): Team
}