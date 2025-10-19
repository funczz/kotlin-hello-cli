/**
 * Application
 */
object Application {
    const val MAIN_CLASS = "com.github.funczz.kotlin.hello.cli.MainClass"
    const val TITLE = "hello-cli"
    const val VERSION = "0.1"
    const val SPLASHSCREEN_IMAGE = ""
}

/**
 * plugins
 */
plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.9.22"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

/**
 * build script
 */
buildscript {
    /**
     * repositories
     */
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

/**
 * repositories
 */
repositories {
    mavenLocal()
    mavenCentral()
    maven { setUrl("https://funczz.github.io/kotlin-junit5-cases") }
}

/**
 * plugins
 */
apply(plugin = "java")
apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "jacoco")

/**
 * dependencies
 */
dependencies {

    //libs Directory
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")

    //Command line parsing library for Java
    implementation("org.jcommander:jcommander:3.0")

    //HTML Parser - jsoup is a Java library that simplifies working with real-world HTML and XML.
    implementation("org.jsoup:jsoup:1.21.2")

    //Gson is a Java library that can be used to convert Java Objects into their JSON representation.
    implementation("com.google.code.gson:gson:2.13.2")

    //Square’s meticulous HTTP client for Java and Kotlin. 
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    testImplementation("com.github.funczz:junit5-cases:0.2.0")

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

/**
 * task: JavaCompile
 */

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

/**
 * task: KotlinCompile
 */
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

/**
 * task: Test
 */
tasks.withType(Test::class.java) {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

/**
 * plugin: application
 */
application {
    mainClass.set(Application.MAIN_CLASS)
}

fun Manifest.setApplicationAttributes() {
    this.apply {
        attributes["Main-Class"] = Application.MAIN_CLASS
        attributes["Implementation-Title"] = Application.TITLE
        attributes["Implementation-Version"] = Application.VERSION
        attributes["Splashscreen-Image"] = Application.SPLASHSCREEN_IMAGE
    }
}

val run by tasks.getting(JavaExec::class) {
    if (project.hasProperty("args")) {
        args = (project.property("args") as String).split("""\s+""".toRegex())
    }
    standardInput = System.`in`
}

/**
 * task: jar
 */
val jar by tasks.getting(Jar::class) {
    manifest.setApplicationAttributes()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

/**
 * task: fatJar
 */
val fatJar = task("fatJar", type = Jar::class) {
    group = "Build"
    description = "Assembles a fat jar archive."
    archiveBaseName.set("${archiveBaseName.get()}-fat")
    manifest.setApplicationAttributes()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    with(tasks["jar"] as CopySpec)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}
