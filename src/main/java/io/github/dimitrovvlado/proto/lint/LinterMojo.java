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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Goal which executes the lint.
 */
@Mojo(name = "lint", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class LinterMojo extends AbstractMojo {

    @Component(role = MavenSession.class)
    protected MavenSession session;

    @Parameter(property = "basedir", defaultValue = "${project.basedir}", required = true)
    protected File basedir;

    @Parameter(property = "protoFiles")
    protected File[] protoFiles;

    @Parameter(property = "protoDirectory")
    private File protoDirectory;

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
        getLog().info("Successfully verified " + files.size() + " file(s).");
    }

    private Collection<File> resolveFiles() throws MojoExecutionException {
        List<File> protos = new LinkedList<>();
        if ((protoFiles == null || protoFiles.length == 0) && protoDirectory == null) {
            File srcDir = new File(basedir, "src");
            if (srcDir.exists() && srcDir.isDirectory()) {
                protos.addAll(walkDirectory(srcDir));
            } else {
                getLog().warn("Src directory does not exist or is not a folder");
            }
        } else {
            if (protoFiles != null) {
                protos.addAll(Arrays.asList(protoFiles));
            }
            if (protoDirectory != null) {
                if (!protoDirectory.exists() || !protoDirectory.isDirectory()) {
                    throw new MojoExecutionException("'" + protoDirectory.getName() +
                            "' does not exist or is not a directory");
                }
                protos.addAll(walkDirectory(protoDirectory));
            }
        }
        return protos;
    }

    boolean skipLinting() {
        final String packaging = session.getCurrentProject().getPackaging();
        if ("pom".equalsIgnoreCase(packaging)) {
            getLog().info("Project packaging is " + packaging);
            return true;
        }
        return false;
    }

    private Collection<File> walkDirectory(File protoDirectory) throws MojoExecutionException {
        try (Stream<Path> paths = Files.walk(protoDirectory.toPath())) {
            return paths.
                    filter(path -> path.toFile().exists() && getFileExtension(path).equalsIgnoreCase("proto")).
                    map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new MojoExecutionException("Error traversing " + protoDirectory.getName(), e);
        }
    }

    private String getFileExtension(Path path) {
        Path name = path.getFileName();
        // null for empty paths and root-only paths
        if (name == null) {
            return "";
        }
        String fileName = name.toString();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }
}
