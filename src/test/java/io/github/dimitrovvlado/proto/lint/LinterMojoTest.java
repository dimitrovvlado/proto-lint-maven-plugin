package io.github.dimitrovvlado.proto.lint;


import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LinterMojoTest {
    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() {
        }

        @Override
        protected void after() {
        }
    };

    @Test
    public void testProjectWithSingleProtoFile() throws Exception {
        File projectDirectory = new File("target/test-classes/project-with-single-proto/");
        assertNotNull(projectDirectory);
        assertTrue(projectDirectory.exists());

        LinterMojo linterMojo = (LinterMojo) rule.lookupConfiguredMojo(projectDirectory, "lint");
        assertNotNull(linterMojo);
        linterMojo.execute();

//        File outputDirectory = (File) rule.getVariableValueFromObject(linterMojo, "outputDirectory");
//        assertNotNull(outputDirectory);
//        assertTrue(outputDirectory.exists());

    }

    @Test
    public void testProjectWithSingleProtoDirectory() throws Exception {
        File projectDirectory = new File("target/test-classes/project-with-proto-directory/");
        assertNotNull(projectDirectory);
        assertTrue(projectDirectory.exists());

        LinterMojo linterMojo = (LinterMojo) rule.lookupConfiguredMojo(projectDirectory, "lint");
        assertNotNull(linterMojo);
        linterMojo.execute();

//        File outputDirectory = (File) rule.getVariableValueFromObject(linterMojo, "outputDirectory");
//        assertNotNull(outputDirectory);
//        assertTrue(outputDirectory.exists());

    }
}

