package sollecitom.taskmanager.domain.task

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class InMemoryTasksContainer : TasksContainer {

    private val _tasks = mutableSetOf<Task>()
    override val tasks: Flow<Task> get() = _tasks.asFlow()

    override fun addTask(task: Task) {

        _tasks += task
    }

    override fun removeTask(task: Task) {

        _tasks -= task
    }
}