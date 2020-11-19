package sollecitom.taskmanager.domain.task

import kotlinx.coroutines.flow.Flow

interface TasksContainer {

    val tasks: Flow<Task>

    fun addTask(task: Task)

    fun removeTask(task: Task)
}