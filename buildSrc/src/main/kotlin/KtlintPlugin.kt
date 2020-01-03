import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

class KtlintPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val outputDir = "${project.buildDir}/reports/ktlint/"
        val inputDir = "src"
        val includeFiles = "**/*.kt"
        val inputFiles =
            project.fileTree(mutableMapOf("dir" to inputDir, "include" to includeFiles))
        val outputFile = "${outputDir}ktlint-checkstyle-report.xml"

        val ktlintConfig =
            if (!project.configurations.names.contains("ktlint")) {
                project.configurations.create("ktlint") {
                    project.dependencies {
                        add("ktlint", Libraries.ktlint)
                    }
                }
            } else {
                project.configurations["ktlint"]
            }

        project.tasks.register("ktlint", JavaExec::class.java) {
            group = "verification"

            description = "Check Kotlin code style."
            classpath = ktlintConfig
            main = "com.pinterest.ktlint.Main"
            args("--android", "-F", "$inputDir/$includeFiles")
        }

        project.tasks.register("ktlintFormat", JavaExec::class.java) {
            inputs.files(inputFiles)
            outputs.file(outputFile)
            outputs.upToDateWhen { true }

            group = "formatting"

            description = "Fix Kotlin code style deviations."
            classpath = ktlintConfig
            main = "com.pinterest.ktlint.Main"
            args(
                "--color",
                "--android",
                "-F",
                "--reporter=plain",
                "--reporter=checkstyle,output=$outputFile",
                "$inputDir/$includeFiles"
            )
        }

        project.tasks.register("ktlintFormatAll", JavaExec::class.java) {
            group = "formatting"

            description = "Fix Kotlin code style deviations."
            classpath = ktlintConfig
            main = "com.pinterest.ktlint.Main"
            args("--color", "--android", "-F", "$inputDir/$includeFiles")
        }
    }
}
