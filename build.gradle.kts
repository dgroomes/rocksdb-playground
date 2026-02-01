plugins {
    java
    application
}

repositories {
    mavenCentral()
}

val slf4jVersion = "2.0.17" // releases: http://www.slf4j.org/news.html

// RocksDB releases: https://github.com/facebook/rocksdb/releases
//
// Note: for some reason the RocksDB JNI releases lag (and skip?) some versions so you need to refer to Maven Central
// for the actual available versions: https://mvnrepository.com/artifact/org.rocksdb/rocksdbjni
val rocksDbVersion = "10.4.2"

val jacksonVersion = "3.0.4" // Jackson releases: https://github.com/FasterXML/jackson/wiki/Jackson-Releases

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("tools.jackson.core:jackson-databind:$jacksonVersion")
    implementation("org.rocksdb:rocksdbjni:$rocksDbVersion:osx")
}

tasks.register<JavaExec>("runBasic") {
    group = "application"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("dgroomes.BasicDemo")
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}

tasks.register<JavaExec>("runBatch") {
    group = "application"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("dgroomes.BatchDemo")
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}
