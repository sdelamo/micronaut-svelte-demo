package example.micronaut

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DeleteSpec
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskProvider

@CompileStatic
class FrontEndPlugin implements Plugin<Project> {
    public static final String EXTENSION_NPM = "npm"
    public static final String TASK_NPM_BUILD = "npmBuild"
    public static final String TASK_NPM_INSTALL = "npmInstall"

    @Override
    void apply(Project project) {
        project.getPlugins().apply(BasePlugin)
        project.extensions.create(EXTENSION_NPM, NpmExtension)
        Object extension = project.getExtensions().findByName(EXTENSION_NPM)
        NpmExtension npmExtension
        if (extension instanceof NpmExtension) {
            npmExtension = ((NpmExtension) extension)
        } else {
            throw new GradleException("npm extension should be of type NpmExtension")
        }
        project.tasks.register(TASK_NPM_INSTALL, NpmInstallTask, { task ->
            task.setGroup("npm")
            task.setProperty("npmPath", npmExtension.npmPath)
            task.setProperty("nodeModules", npmExtension.nodeModules)
            task.setProperty("packageJson", npmExtension.packageJson)
        })
        TaskProvider<NpmBuildTask> npmBuildTaskTaskProvider = project.tasks.register(TASK_NPM_BUILD, NpmBuildTask, { task ->
            task.setGroup("npm")
            task.dependsOn(TASK_NPM_INSTALL)
            task.setProperty("publicOutput", npmExtension.publicOutput)
            task.setProperty("npmPath", npmExtension.npmPath)
            task.setProperty("packageJson", npmExtension.packageJson)
            task.setProperty("src", npmExtension.src)
            task.setProperty("scripts", npmExtension.scripts)
            task.setProperty("rollupConfig", npmExtension.rollupConfig)
            task.setProperty("src", npmExtension.src)
            task.setProperty("scripts", npmExtension.scripts)
        })
        configureConfigurations(project, npmBuildTaskTaskProvider)
    }

    @CompileDynamic
    void configureConfigurations(Project project, TaskProvider<NpmBuildTask> npmBuildTaskTaskProvider) {
        project.configurations {
            ui {
                canBeResolved = false
                canBeConsumed = true // this is an *outgoing* configuration
                outgoing.artifact(npmBuildTaskTaskProvider)
            }
        }
    }
}
