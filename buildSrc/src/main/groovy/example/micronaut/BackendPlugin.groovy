package example.micronaut

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.TaskProvider

class BackendPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.getPlugins().apply(BasePlugin)
        project.configurations {
            ui {
                canBeResolved = true
                canBeConsumed = false
            }
        }
        TaskProvider<CopyPublic> copyPublicTaskProvider = project.tasks.register("copyPublic", CopyPublic) {task ->
            contents.from(project.configurations.ui)
            task.outputDirectory = project.layout.buildDirectory.dir("external-resources")
        }
        project.sourceSets.main.resources.srcDir(copyPublicTaskProvider)
    }
}
