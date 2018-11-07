package io.github.dimitrovvlado.proto.lint.validation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaseFormatTest {

    @Test
    public void testUpperUnderscore() {
        assertEquals(CaseFormat.UPPER_UNDERSCORE, CaseFormat.getCase("UPPER_UNDERSCORE"));
        assertEquals(CaseFormat.UPPER_UNDERSCORE, CaseFormat.getCase("UPPER_UNDERSCORE9"));
        assertEquals(CaseFormat.UPPER_UNDERSCORE, CaseFormat.getCase("UPPER9_UNDERSCORE9"));
    }

    @Test
    public void testLowerUnderscore() {
        assertEquals(CaseFormat.LOWER_UNDERSCORE, CaseFormat.getCase("lower_underscore"));
        assertEquals(CaseFormat.LOWER_UNDERSCORE, CaseFormat.getCase("lower_underscore9"));
        assertEquals(CaseFormat.LOWER_UNDERSCORE, CaseFormat.getCase("lower9_underscore"));
    }

    @Test
    public void testLowerHyphen() {
        assertEquals(CaseFormat.LOWER_HYPHEN, CaseFormat.getCase("lower-hyphen"));
        assertEquals(CaseFormat.LOWER_HYPHEN, CaseFormat.getCase("lower-hyphen9"));
        assertEquals(CaseFormat.LOWER_HYPHEN, CaseFormat.getCase("lower9-hyphen"));
    }

    @Test
    public void testLowerCamel() {
        assertEquals(CaseFormat.LOWER_CAMEL, CaseFormat.getCase("lowerCamel"));
        assertEquals(CaseFormat.LOWER_CAMEL, CaseFormat.getCase("lowerCamel9"));
        assertEquals(CaseFormat.LOWER_CAMEL, CaseFormat.getCase("lower9Camel"));
    }

    @Test
    public void testUpperCamel() {
        assertEquals(CaseFormat.UPPER_CAMEL, CaseFormat.getCase("Camel"));
        assertEquals(CaseFormat.UPPER_CAMEL, CaseFormat.getCase("UpperCamel"));
        assertEquals(CaseFormat.UPPER_CAMEL, CaseFormat.getCase("UpperCamel9"));
        assertEquals(CaseFormat.UPPER_CAMEL, CaseFormat.getCase("Upper9Camel"));
    }

    @Test
    public void testAllLower() {
        assertEquals(CaseFormat.ALL_LOWER, CaseFormat.getCase("lower"));
        assertEquals(CaseFormat.ALL_LOWER, CaseFormat.getCase("lower9"));
    }

    @Test
    public void testAllUper() {
        assertEquals(CaseFormat.ALL_UPPER, CaseFormat.getCase("UPPER"));
        assertEquals(CaseFormat.ALL_UPPER, CaseFormat.getCase("UPPER9"));
    }

    @Test
    public void testInvalid() {
        assertNull(CaseFormat.getCase("invalid-INVALID"));
        assertNull(CaseFormat.getCase("INVALID-invalid"));
        assertNull(CaseFormat.getCase("invalid_INVALID"));
        assertNull(CaseFormat.getCase("INVALID_invalid"));
        assertNull(CaseFormat.getCase("Invalid_Invalid"));
        assertNull(CaseFormat.getCase("iNvalid_iNvalid"));
        assertNull(CaseFormat.getCase("INVALID-INVALID"));
        assertNull(CaseFormat.getCase("9invalid"));
        assertNull(CaseFormat.getCase("9INVALID"));
    }
}
