package example.micronaut

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import javax.inject.Inject;

@CompileStatic
abstract class NpmInstallTask extends DefaultTask {
    @OutputDirectory
    final Property<File> nodeModules = project.objects.property(File)

    @Input
    final Property<String> npmPath = project.objects.property(String)

    @Input
    final Property<File> packageJson = project.objects.property(File)

    @Inject
    abstract ExecOperations getExecOperations()

    @TaskAction
    void generateProductionStaticWebsite() {
        execOperations.exec {
            it.commandLine(npmPath.get(),"install")
        }
    }
}
