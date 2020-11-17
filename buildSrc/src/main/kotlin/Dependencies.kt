import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

object Dependencies {
    fun indexlabsCommons(name: String, version: String = Versions.indexlabsCommons): ModuleDependency = DefaultExternalModuleDependency("com.indexlabs.commons", name, version).setChanging(true)

    fun micronautBom(version: String = Versions.micronaut): ModuleDependency = micronaut("micronaut-bom", version)
    fun micronautInject(version: String = Versions.micronaut): ModuleDependency = micronaut("micronaut-inject-java", version)
    fun micronautRuntime(version: String = Versions.micronaut): ModuleDependency = micronaut("micronaut-runtime", version)
    fun micronautHttpServerNetty(version: String = Versions.micronaut): ModuleDependency = micronaut("micronaut-http-server-netty", version)
    fun micronautTestJunit5(version: String = Versions.micronautTest): ModuleDependency = micronautTest("micronaut-test-junit5", version)
    fun micronautPrometheus(version: String = Versions.micronautPrometheus): ModuleDependency = DefaultExternalModuleDependency("io.micronaut.configuration" , "micronaut-micrometer-registry-prometheus", version)
    fun micronautKubernetesDiscoveryClient(version: String = Versions.micronautKubernetes): ModuleDependency = DefaultExternalModuleDependency("io.micronaut.kubernetes", "micronaut-kubernetes-discovery-client", version)

    fun micronautCache(version: String = Versions.micronautCache): ModuleDependency = DefaultExternalModuleDependency("io.micronaut.cache" , "micronaut-cache-caffeine", version)

    fun micronautGrpcServer(version: String = Versions.micronaut): ModuleDependency = DefaultExternalModuleDependency("io.micronaut.grpc", "micronaut-grpc-server-runtime", version)
    fun micronautGrpcClient(version: String = Versions.micronaut): ModuleDependency = DefaultExternalModuleDependency("io.micronaut.grpc", "micronaut-grpc-client-runtime", version)

    fun log4j2Slf4jAdapter(version: String = Versions.log4j2): ModuleDependency = DefaultExternalModuleDependency("org.apache.logging.log4j", "log4j-slf4j-impl", version)

    private fun micronaut(name: String, version: String = Versions.micronaut): ModuleDependency = DefaultExternalModuleDependency("io.micronaut", name, version)
    private fun micronautTest(name: String, version: String = Versions.micronautTest): ModuleDependency = DefaultExternalModuleDependency("io.micronaut.test", name, version)
}
