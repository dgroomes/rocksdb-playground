val rocksDbVersion = "6.14.6" // releases: https://github.com/facebook/rocksdb/releases

plugins {
    `java-library`
}

dependencies {
    api(project(":repo"))
    implementation("org.rocksdb:rocksdbjni:$rocksDbVersion")
}
