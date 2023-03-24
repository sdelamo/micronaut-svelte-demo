package example.micronaut

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import javax.inject.Inject;

@CompileStatic
abstract class NpmBuildTask extends DefaultTask {
    @OutputDirectory
    final Property<File> publicOutput = project.objects.property(File)

    @InputDirectory
    final Property<File> src = project.objects.property(File)

    @InputDirectory
    final Property<File> scripts = project.objects.property(File)

    @Input
    final Property<File> packageJson = project.objects.property(File)

    @Input
    final Property<File> rollupConfig = project.objects.property(File)

    @Input
    final Property<String> npmPath = project.objects.property(String)

    @Inject
    abstract ExecOperations getExecOperations()

    @TaskAction
    void generateProductionStaticWebsite() {
        execOperations.exec {
            it.commandLine(npmPath.get(), "run", "build")
        }
    }
}
