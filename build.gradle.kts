plugins {
    id("application")
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
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
    implementation("org.jetbrains:annotations:24.0.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
    version = "17"
}