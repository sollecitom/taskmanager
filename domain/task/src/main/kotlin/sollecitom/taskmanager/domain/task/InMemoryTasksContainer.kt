package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class InMemoryTasksContainer(override val id: Id) : TasksContainer {

    private val _tasks = mutableSetOf<Task>()
    override val tasks: Flow<Task> get() = _tasks.asFlow()

    override fun addTask(task: Task) {

        _tasks += task
    }

    override fun removeTask(task: Task) {

        _tasks -= task
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InMemoryTasksContainer

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()
}