var jooqVersion: String = "3.17.5"

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.flywaydb.flyway") version "9.20.1"
}

group = "nl.tijsbeek"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("nl.tijsbeek.pictureprojectstool.Main")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("net.rgielen:javafx-weaver-spring-boot-starter:1.3.0")


    implementation("org.jetbrains:annotations:24.0.0")
    implementation("dev.brachtendorf:JImageHash:1.0.0")

    // Database
    implementation("org.flywaydb:flyway-core:9.20.1")
    runtimeOnly("org.postgresql:postgresql:42.7.3")

    // Database code generation
    implementation("org.jooq:jooq:$jooqVersion")
    implementation("org.jooq:jooq-meta:$jooqVersion")
    implementation("org.jooq:jooq-codegen:$jooqVersion")

    implementation("org.springframework.boot:spring-boot-starter-jooq:3.3.0") {
        exclude(group = "org.jooq")
        exclude(group = "org.jooq", module = "jooq-meta")
        exclude(group = "org.jooq", module = "jooq-codegen")
    }


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

flyway {
    configFiles = arrayOf("flyway.conf");
}

javafx {
    modules("javafx.controls", "javafx.fxml")
    version = "22"
}