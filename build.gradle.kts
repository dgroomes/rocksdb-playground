plugins {
    java
}

val slf4jVersion = "1.7.30" // releases: http://www.slf4j.org/news.html

subprojects {
    apply(plugin = "java")

    repositories {
        mavenLocal()
        jcenter()
    }

    dependencies {
        "implementation"("org.slf4j:slf4j-api:$slf4jVersion")
        "implementation"("org.slf4j:slf4j-simple:$slf4jVersion")
    }
}
