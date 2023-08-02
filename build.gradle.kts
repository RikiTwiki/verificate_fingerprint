plugins {
    id("org.springframework.boot") version "2.5.5"  // adjust the version if needed
    id("io.spring.dependency-management") version "1.0.11.RELEASE"  // adjust the version if needed
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-data-rest")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter")
    implementation ("org.postgresql:postgresql:42.3.1") // replace with the latest version if needed
    implementation("ch.qos.logback:logback-classic:1.2.9") // Replace 1.2.9 with the latest version if needed
    implementation("com.machinezoo.sourceafis:sourceafis:3.14.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}