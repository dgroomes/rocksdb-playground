plugins {
    java
    id("dgroomes.dependencies-lister")
}

repositories {
    mavenLocal()
    jcenter()
}

val slf4jVersion = "1.7.30" // releases: http://www.slf4j.org/news.html
val rocksDbVersion = "6.14.6" // releases: https://github.com/facebook/rocksdb/releases

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("org.rocksdb:rocksdbjni:$rocksDbVersion")
}
