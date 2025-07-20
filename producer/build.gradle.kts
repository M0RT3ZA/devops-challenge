plugins {
    kotlin("jvm") version "1.9.10"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:3.6.0")
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("ProducerKt")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ProducerKt"
    }
}
