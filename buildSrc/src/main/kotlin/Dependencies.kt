import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

object Dependencies {
    fun indexlabsCommons(name: String, version: String = Versions.indexlabsCommons): ModuleDependency = DefaultExternalModuleDependency("com.indexlabs.commons", name, version).setChanging(true)

    fun log4j2Slf4jAdapter(version: String = Versions.log4j2): ModuleDependency = DefaultExternalModuleDependency("org.apache.logging.log4j", "log4j-slf4j-impl", version)
}
