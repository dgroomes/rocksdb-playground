package dgroomes;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.StringJoiner;

/**
 * List the project's runtime dependencies and print them into a file.
 *
 * This task identifies its inputs and outputs so that Gradle can do its up-to-date checks (a.k.a. Incremental Build)
 * which saves you time when running the task and the inputs haven't changed. It skips the task because it is already
 * "up-to-date".
 */
public class ListDependenciesTask extends DefaultTask {

    public static final String DEPENDENCIES_OUTPUT_FILE_NAME = "runtime-dependencies.txt";

    /**
     * The runtime classpath.
     *
     * This is a Gradle task "input".
     */
    @Classpath
    public FileCollection getRuntimeClasspath() {
        var sourceSets = ((SourceSetContainer) getProject().getExtensions().getByName("sourceSets"));
        var mainSourceSet = sourceSets.getByName("main");
        return mainSourceSet.getRuntimeClasspath();
    }

    /**
     * The file that lists the runtime dependencies.
     *
     * This is a Gradle task "output".
     */
    @OutputFile
    public File getDependenciesListFile() {
        return new File(getProject().getBuildDir(), DEPENDENCIES_OUTPUT_FILE_NAME);
    }

    /**
     * List the project's runtime dependencies. This is given by the Gradle source set named "main". It is printed to
     * "build/runtime-dependencies.txt".
     *
     * NOTE: It is not necessary to create the output file's parent directory because Gradle creates it automatically
     * before it executes the task thanks to org.gradle.internal.execution.steps.CreateOutputsStep.
     */
    @TaskAction
    public void listDependencies() throws IOException {
        FileCollection runtimeClasspath = getRuntimeClasspath();

        // Join the dependencies by the colon character. This format is friendly as an argument to describe a classpath.
        var joiner = new StringJoiner(":");
        for (File file : runtimeClasspath) {
            if (file.isFile()) {
                joiner.add(file.toString());
            }
        }
        var joined = joiner.toString();

        // Create the output file "fresh" (i.e. delete it if it already existed from an earlier execution of the task)
        var file = getDependenciesListFile();
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        // Write the contents to the file
        Files.writeString(file.toPath(), joined);
    }
}
