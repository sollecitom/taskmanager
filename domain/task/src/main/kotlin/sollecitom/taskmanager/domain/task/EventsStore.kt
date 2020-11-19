package sollecitom.taskmanager.domain.task

import kotlinx.coroutines.flow.Flow

interface EventsStore<EVENT : Event> {

    suspend fun publish(event: EVENT)

    val events: Flow<EVENT>
}