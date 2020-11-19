package sollecitom.taskmanager.domain.task

import com.indexlabs.commons.domain.identity.Id
import java.time.Instant

interface ProductsFactory {

    suspend fun create(id: Id, createdBy: Id, createdAt: Instant): Product
}