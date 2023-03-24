package example.micronaut

import groovy.transform.CompileStatic
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

import javax.inject.Inject

@CompileStatic
class NpmExtension {

    final Property<File> nodeModules
    final Property<String> npmPath
    final Property<File> packageJson
    final Property<File> src
    final Property<File> publicOutput
    final Property<File> scripts
    final Property<File> rollupConfig

    @Inject
    NpmExtension(ObjectFactory objects) {
        publicOutput = objects.property(File).convention(new File("public"))
        nodeModules = objects.property(File).convention(new File("node_modules"))
        src = objects.property(File).convention(new File("src"))
        scripts = objects.property(File).convention(new File("scripts"))
        npmPath = objects.property(String).convention("/usr/local/bin/npm")
        packageJson = objects.property(File).convention(new File("package.json"))
        rollupConfig = objects.property(File).convention(new File("rollup.config.js"))

    }
}
