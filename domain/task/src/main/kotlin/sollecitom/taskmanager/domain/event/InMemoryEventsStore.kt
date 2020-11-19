package sollecitom.taskmanager.domain.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class InMemoryEventsStore<EVENT : Event> : EventsStore<EVENT> {

    private val _events = MutableSharedFlow<EVENT>()
    override val events = _events.asSharedFlow()

    override suspend fun publish(event: EVENT) = _events.emit(event)
}