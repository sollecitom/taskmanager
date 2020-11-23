package sollecitom.taskmanager.domain.product

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.AbstractEvent
import java.time.Instant

class ProductWasCreated(val product: Product, timestamp: Instant, id: Id = Id.create()) : AbstractEvent(id, timestamp) {

    override val actorId: Id by product::createdBy
}