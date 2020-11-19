package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface TasksContainer {

    val tasks: Flow<Task>

    fun addTask(task: Task, addedBy: Id, addedAt: Instant)
}