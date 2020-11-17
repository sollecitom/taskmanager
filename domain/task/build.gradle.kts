dependencies {
    with(Dependencies) {
        api(indexlabsCommons("domain-functional"))
        api(indexlabsCommons("domain-identity"))
        api(indexlabsCommons("domain-time"))
        api(indexlabsCommons("logging-core"))
        api(indexlabsCommons("domain-tracing"))
        api(indexlabsCommons("domain-service-lifecycle"))

        testImplementation(indexlabsCommons("domain-test"))
    }
}
