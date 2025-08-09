package dgroomes;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.SourceSetContainer;

/**
 * A custom Gradle plugin to register a task to list the project's runtime dependencies in a file.
 */
public class DependenciesListerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("listDependencies", ListDependenciesTask.class, task -> {
            var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            var mainSourceSet = sourceSets.getByName("main");

            task.getRuntimeClasspath().setFrom(mainSourceSet.getRuntimeClasspath());
            task.getDependenciesListFile().set(project.getLayout().getBuildDirectory().file(ListDependenciesTask.DEPENDENCIES_OUTPUT_FILE_NAME));
        });

        Task build = project.getTasks().getByPath("build");
        build.dependsOn("listDependencies");
    }
}
