# WebApp Template

A project template showcasing how to structure a web application.

Includes:

- Component ports and adapters architecture
- Suspending endpoint mappings
- Kotlin
- Gradle 6
- Gradle Kotlin DSL
- Micronaut
- Contract testing

## How To

### Set it up in Intellij

- Enable Annotation Processors
- Add `indexLabsMavenRepositoryToken=<your_api_scoped_token>` to your `.gradle/gradle.properties` file. You can generate an API scoped token from [our GitLab](https://gitlab.infra-shared.footballindex.co.uk/), under `Settings`.
- Add Gradle task "classes" to your Kotlin Run/Debug configuration template.
- Add Gradle task "testClasses" to your JUnit Run/Debug configuration template.
- In Intellij preferences, set up Gradle to run against JDK 15.
- Add your hostname (run `hostname` to find out) to your `/etc/hosts` file alongside localhost e.g., `127.0.0.1 localhost Admins-MacBook-Pro.local` and `::1 localhost Admins-MacBook-Pro.local` [Not strictly needed but will mean Intellij tests run a lot faster].

### Run it

From Intellij, run/debug `com.indexlabs.templates.webapp.starter.Starter`.

The server starts on a random available API and management ports by default, which will be logged at startup, and exposes the following endpoints:

- `/api/greetings/{name}` (GET)
- `/api/greetings` (GET, POST e.g., `{"prefix": "Yo"}`)
- `/api/current-greeting` (GET, PUT e.g., `{"prefix": "Hola"}`)
- `/actuator/health` (GET)

### Build it

Run `./gradlew [clean] build [--refresh-dependencies]` from the project's root directory.

### Build it in Docker image form

Run `./gradlew [clean] build jibDockerBuild` from the project's root directory.

### Run as Docker image

After building it, run `docker run -p8085:8085 -p8095:8095 web-app-template/starter`, and the server will start on port 8085 for API and 8095 for management.

### Load test it

From the project's root directory, run `wrk -t12 -c400 -d1m -s adapters/driving/web/src/test/resources/performance/request.lua http://localhost:8085/api/greetings/michele` after installing [wrk](https://github.com/wg/wrk/wiki).

### Change behaviour at runtime

#### Override default console logging layout

Provide environment property `WEB_APP_TEMPLATE_LOGGING_LAYOUT`, with value either `json` or `plain`, default to `plain`.

#### Enable Slack notifications for errors

Provide environment properties:

- `WEB_APP_TEMPLATE_LOGGING_SLACK_ENABLED` with value either `true` or `false`, default to `false`.
- `WEB_APP_TEMPLATE_LOGGING_SLACK_WEB_HOOK_URL` with value equal to the Slack integration web-hook URL.
