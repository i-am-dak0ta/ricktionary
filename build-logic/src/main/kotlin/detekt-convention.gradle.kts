import com.dak0ta.ricktionary.buildlogic.libs
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.rules.compose)
}

detekt {
    source.from(files("src/main/java", "src/main/kotlin"))
    config.from(files("$rootDir/config/detekt.yml"))
    reportsDir = file("$rootDir/build/reports/detekt")
    buildUponDefaultConfig = true
}

val isCi = System.getenv("CI")?.toBoolean() ?: false
val openDetektReport by tasks.registering(Exec::class) {
    group = "verification"
    description = "Открыть HTML-отчёт Detekt в браузере"

    val reportFile = file("$rootDir/build/reports/detekt/detekt.html")

    doFirst { if (!reportFile.exists()) throw GradleException("Detekt report not found: $reportFile") }

    onlyIf { !isCi && reportFile.readText().contains("<h2>Findings</h2>\n<div>Total: 0</div>").not() }

    commandLine(
        when {
            org.gradle.internal.os.OperatingSystem.current().isWindows -> listOf("cmd", "/c", reportFile.absolutePath)
            org.gradle.internal.os.OperatingSystem.current().isMacOsX -> listOf("open", reportFile.absolutePath)
            else -> listOf("xdg-open", reportFile.absolutePath)
        },
    )
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        txt.required.set(false)
        xml.required.set(false)
        sarif.required.set(false)
    }
    if (!isCi) {
        finalizedBy(openDetektReport)
    }
}
