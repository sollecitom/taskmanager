package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import com.indexlabs.commons.domain.identity.Identifiable
import kotlinx.coroutines.flow.Flow

interface TasksContainer : Identifiable<Id> {

    val tasks: Flow<Task>

    fun addTask(task: Task)

    fun removeTask(task: Task)
}