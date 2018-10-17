package io.github.dimitrovvlado.proto.lint;


import io.github.dimitrovvlado.proto.lint.validation.ValidationResult;
import io.github.dimitrovvlado.proto.lint.validation.ValidatorService;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Goal which executes the lint.
 */
@Mojo(name = "lint", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class LinterMojo extends AbstractMojo {

    @Component(role = MavenSession.class)
    protected MavenSession session;

    @Parameter(defaultValue = "${project.basedir}", property = "basedir", required = true)
    protected File basedir;

    @Parameter(property = "protoFiles")
    protected String[] protoFiles;

    @Parameter(property = "protoDirectory")
    private String protoDirectory;

    private ValidatorService validator;

    public LinterMojo() {
        validator = ValidatorService.getInstance();
    }

    public void execute() throws MojoExecutionException {
        if (skipLinting()) {
            getLog().info("Skipping linting phase.");
            return;
        }
        Collection<File> files = resolveFiles();
        int errorCount = 0;
        for (File file : files) {
            if (getLog().isDebugEnabled()) {
                getLog().debug("Validating " + file.getName());
            }
            try {
                List<ValidationResult> result = validator.apply(file);
                for (ValidationResult r : result) {
                    errorCount += r.apply(getLog());
                }
            } catch (IOException e) {
                getLog().error(e);
                throw new MojoExecutionException("Error validating file " + file.getName(), e);
            }
        }
        if (errorCount > 0) {
            throw new MojoExecutionException(errorCount + " errors encountered.");
        }
    }

    private Collection<File> resolveFiles() {
        System.out.println("BASEDIR: " + basedir);
        //TODO not handling folders
        return Arrays.stream(protoFiles).map(n -> new File(basedir, n)).filter(f -> {
            if (!f.exists()) {
                //TODO Break build instead of printing an error
                getLog().error("File not found: " + f.getName());
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    private boolean skipLinting() {
        final String packaging = session.getCurrentProject().getPackaging();
        if ("pom".equalsIgnoreCase(packaging)) {
            getLog().info("Project packaging is " + packaging);
            return true;
        }
        return false;
    }
}
