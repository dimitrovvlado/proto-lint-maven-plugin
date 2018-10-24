package io.github.dimitrovvlado.proto.lint;


import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

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

        File[] protoFiles = (File[]) rule.getVariableValueFromObject(linterMojo, "protoFiles");
        assertNotNull(protoFiles);
        assertEquals(1, protoFiles.length);
        assertTrue(protoFiles[0].exists());
    }

    @Test
    public void testProjectWithSingleProtoDirectory() throws Exception {
        File projectDirectory = new File("target/test-classes/project-with-proto-directory/");
        assertNotNull(projectDirectory);
        assertTrue(projectDirectory.exists());

        LinterMojo linterMojo = (LinterMojo) rule.lookupConfiguredMojo(projectDirectory, "lint");
        assertNotNull(linterMojo);
        linterMojo.execute();

        File protoDirectory = (File) rule.getVariableValueFromObject(linterMojo, "protoDirectory");
        assertNotNull(protoDirectory);
        assertTrue(protoDirectory.exists());
    }

    @Test
    public void testProjectWithDefaultProtoDirectory() throws Exception {
        File projectDirectory = new File("target/test-classes/project-with-default-directory/");
        assertNotNull(projectDirectory);
        assertTrue(projectDirectory.exists());

        LinterMojo linterMojo = (LinterMojo) rule.lookupConfiguredMojo(projectDirectory, "lint");
        assertNotNull(linterMojo);
        linterMojo.execute();

        assertNull(rule.getVariableValueFromObject(linterMojo, "protoDirectory"));
        File[] protoFiles = (File[])rule.getVariableValueFromObject(linterMojo, "protoFiles");
        assertNotNull(protoFiles);
        assertEquals(0, protoFiles.length);
    }
}

