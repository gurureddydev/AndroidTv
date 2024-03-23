plugins{
    `kotlin-dsl`
}
repositories {
    // Add repositories here
    mavenCentral() // Or any other repository that hosts the required dependencies
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
}