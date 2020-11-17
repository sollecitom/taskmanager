import com.palantir.gradle.gitversion.GitVersionPlugin
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    kotlin("plugin.allopen") version Versions.kotlin
    `java-library`
    idea
    id("com.palantir.git-version") version Versions.palantirGitPlugin
}

val parentProject = this
val indexLabsMavenRepositoryUrl: String by project
val indexLabsMavenRepositoryToken: String by project

val gitlabJobToken: String? = System.getenv("CI_JOB_TOKEN")
val mavenRepoTokenName = if (gitlabJobToken != null && gitlabJobToken.isNotEmpty()) "Job-Token" else "Private-Token"
val mavenRepoTokenValue = if (gitlabJobToken != null && gitlabJobToken.isNotEmpty()) gitlabJobToken else indexLabsMavenRepositoryToken

allprojects {
    group = "com.indexlabs.templates.web-app"
    version = "1.0-SNAPSHOT"

    buildscript {
        repositories {
            mavenLocal()
            mavenCentral()
            jcenter()
            maven("https://palantir.bintray.com/releases")
            maven("https://jitpack.io")
            maven(indexLabsMavenRepositoryUrl) {
                credentials(HttpHeaderCredentials::class) {
                    name = mavenRepoTokenName
                    value = mavenRepoTokenValue
                }
                authentication {
                    register("header", HttpHeaderAuthentication::class)
                }
            }
        }
    }

    apply<GitVersionPlugin>()
    apply<IdeaPlugin>()

    idea {
        module {
            inheritOutputDirs = true
            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://palantir.bintray.com/releases")
        maven("https://repo.spring.io/milestone")
        maven("https://jitpack.io")
        maven(indexLabsMavenRepositoryUrl) {
            credentials(HttpHeaderCredentials::class) {
                name = mavenRepoTokenName
                value = mavenRepoTokenValue
            }
            authentication {
                register("header", HttpHeaderAuthentication::class)
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.apply {
            allWarningsAsErrors = true
            jvmTarget = "14"
            javaParameters = true
            freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
        }
    }
}

subprojects {
    apply {
        plugin<IdeaPlugin>()
        plugin<JavaPlugin>()
        plugin<JavaLibraryPlugin>()
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.allopen")
    }

    buildscript {
        repositories {
            addAll(parentProject.buildscript.repositories)
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_15
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        maxParallelForks = Runtime.getRuntime().availableProcessors() * 2
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_15
        targetCompatibility = JavaVersion.VERSION_15
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
    }
}

kapt {
    useBuildCache = false
    correctErrorTypes = true
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

configurations.all {
    exclude(group = "org.slf4j")
    exclude(group = "ch.qos.logback")
    resolutionStrategy {
        preferProjectModules()
    }
}
