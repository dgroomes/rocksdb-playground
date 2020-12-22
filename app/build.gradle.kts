plugins {
    application
}

dependencies {
    implementation(project(":rocksdb-repo"))
    implementation(project(":sqlite-repo"))
}

application {
    mainClass.set("dgroomes.App")
}
