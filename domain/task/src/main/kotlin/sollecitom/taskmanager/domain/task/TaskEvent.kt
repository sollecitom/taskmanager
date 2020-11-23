package sollecitom.taskmanager.domain.task

import sollecitom.taskmanager.domain.event.Event

interface TaskEvent : Event {

    val task: Task
}