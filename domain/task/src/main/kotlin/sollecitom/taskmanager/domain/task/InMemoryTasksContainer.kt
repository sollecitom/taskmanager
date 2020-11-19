package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.time.Instant

class InMemoryTasksContainer : TasksContainer {

    private val storage = mutableSetOf<Task>()

    override val tasks: Flow<Task> get() = storage.asFlow()

    override fun addTask(task: Task) {

        storage += task
    }

    override fun removeTask(task: Task) {

        storage -= task
    }
}