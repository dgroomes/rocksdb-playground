plugins {
    java
    application
}

repositories {
    mavenCentral()
}

val slf4jVersion = "2.0.17" // releases: http://www.slf4j.org/news.html
val rocksDbVersion = "10.2.1" // releases: https://github.com/facebook/rocksdb/releases

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("org.rocksdb:rocksdbjni:$rocksDbVersion")
}

application {
    mainClass.set("dgroomes.Main")
}
