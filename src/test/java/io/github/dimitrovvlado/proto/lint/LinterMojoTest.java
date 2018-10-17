package io.github.dimitrovvlado.proto.lint;


import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;

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
        File pom = new File("target/test-classes/project-with-single-proto/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        LinterMojo linterMojo = (LinterMojo) rule.lookupConfiguredMojo(pom, "lint");
        assertNotNull(linterMojo);
        linterMojo.execute();

//        File outputDirectory = (File) rule.getVariableValueFromObject(linterMojo, "outputDirectory");
//        assertNotNull(outputDirectory);
//        assertTrue(outputDirectory.exists());

    }

}

