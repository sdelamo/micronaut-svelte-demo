package example.micronaut

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileSystemOperations;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

abstract class CopyPublic extends DefaultTask {
    @InputFiles
    abstract ConfigurableFileCollection getContents();

    @OutputDirectory
    abstract DirectoryProperty getOutputDirectory();

    @Inject
    abstract FileSystemOperations getFs()

    @TaskAction
    void copy() {
        File outputDir = outputDirectory.get().asFile
        fs.copy {
            into(new File(outputDir, "static"))
            from(contents.files)
        }
    }
}
