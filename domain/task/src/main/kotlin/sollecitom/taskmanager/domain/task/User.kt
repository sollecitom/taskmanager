package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable

interface User : Identifiable<Id> {

    suspend fun createTask(taskId: Id = Id.create()): Task

    suspend fun createProduct(productId: Id = Id.create()): Product

    suspend fun add(task: Task, container: TasksContainer)
}