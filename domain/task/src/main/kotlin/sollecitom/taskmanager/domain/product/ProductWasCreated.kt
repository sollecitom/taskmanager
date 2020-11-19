package sollecitom.taskmanager.domain.product

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event
import java.time.Instant

class ProductWasCreated(val product: Product, timestamp: Instant, id: Id = Id.create()) : Event(id, timestamp) {

    override val actorId: Id by product::createdBy
}