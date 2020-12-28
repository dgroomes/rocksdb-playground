package dgroomes;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * A custom Gradle plugin to register a task to list the project's runtime dependencies in a file.
 */
public class DependenciesListerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("listDependencies", ListDependenciesTask.class);

        Task build = project.getTasks().getByPath("build");
        build.dependsOn("listDependencies");
    }
}
