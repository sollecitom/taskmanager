package sollecitom.taskmanager.domain.product

import com.indexlabs.commons.domain.identity.Id
import sollecitom.taskmanager.domain.event.Event

class ProductWasCreated(val product: Product, id: Id = Id.create()) : Event(id) {

    override val actorId: Id by product::createdBy
}