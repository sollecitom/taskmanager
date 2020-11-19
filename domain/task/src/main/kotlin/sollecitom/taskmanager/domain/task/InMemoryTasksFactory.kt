package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

class InMemoryTasksFactory : TasksFactory {

    override suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Task = TaskInfo(id, createdBy, createdAt)
}