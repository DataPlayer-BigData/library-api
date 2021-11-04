package com.codecompass.libraryapi.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class LibraryApiUtilsTest {

    @Test
    public void doesStringValueExist() {

        assertTrue(LibraryApiUtils.doesStringValueExist(("valueexisit")));
        assertFalse(LibraryApiUtils.doesStringValueExist(""));
        assertFalse(LibraryApiUtils.doesStringValueExist(null));
    }
}