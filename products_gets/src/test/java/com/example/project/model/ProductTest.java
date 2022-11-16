package com.example.project.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void ensureNameMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Product(null));
    }

    @Test
    void ensureNameMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Product(""));
    }

    @Test
    void ensureNameMustNotBeBlankSpaces() {
        assertThrows(IllegalArgumentException.class, () -> new Product("     "));
    }

    @Test
    void ensureNameIsSet() {
        final var subject = new Product("name");
        assertEquals("name", subject.getName());
    }

    @Test
    void ensureDescriptionIsSet() {
        final var subject = new Product("name");
        subject.setDescription("description test");
        assertEquals("description test", subject.getDescription());
    }

    @Test
    void ensureSkuIsSet() {
        final var subject = new Product("name");
        subject.setSku("AG5H73");
        assertEquals("AG5H73", subject.getSku());
    }

}
