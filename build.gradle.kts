import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.6.10"
    application
}

group = "me.ronklas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    /// Own dependencies:
    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    /*testI*/implementation("org.slf4j:slf4j-simple:2.0.0-alpha6")
    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.19")
    // https://mvnrepository.com/artifact/org.codehaus.jettison/jettison
    implementation("org.codehaus.jettison:jettison:1.4.1")
    // From here: https://github.com/Kotlin/kotlinx.serialization
    // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-cbor
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.3.2")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}