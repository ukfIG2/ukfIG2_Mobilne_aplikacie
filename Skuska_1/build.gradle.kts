// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
test
}

configurations {
    all {
        exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }
}
