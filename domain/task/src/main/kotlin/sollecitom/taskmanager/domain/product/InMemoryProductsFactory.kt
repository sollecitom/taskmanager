package sollecitom.taskmanager.domain.product

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.task.InMemoryTasksContainer
import sollecitom.taskmanager.domain.task.TasksContainer
import java.time.Instant

class InMemoryProductsFactory : ProductsFactory {

    override suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Product = ProductInfo(createdBy, createdAt, newTasksContainer(id))

    private fun newTasksContainer(id: Id): TasksContainer = InMemoryTasksContainer(id)
}