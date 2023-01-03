package com.example.project.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.Test;

import com.example.project.model.Review;

public class ReviewsTest {

    @Test
    void ensureTextMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Review(null));
    }

    @Test
    void ensureTextMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Review(""));
    }


    @Test
    void ensureTextMustNotBeBlankSpaces() {
        assertThrows(IllegalArgumentException.class, () -> new Review("     "));
    }

    @Test
    void ensureTextIsSet() {
        final var subject = new Review("Muito Boa");
        assertEquals("Muito Boa", subject.getText());
    }
    @Test
    void ensureRatingIsSet() {
        final var subject = new Review("5");
        assertEquals("5", subject.getRating());
    }
    @Test
    void ensureUpVoteIsSet() {
        final var subject = new Review("name");
        subject.setUpVote(50);
        assertEquals(Integer.valueOf(50), subject.getUpVote());
    }
    @Test
    void ensureDownVoteIsSet() {
        final var subject = new Review("name");
        subject.setDownVote(50);
        assertEquals(Integer.valueOf(50), subject.getDownVote());
    }
    @Test
    void ensureDataTimeIsSet() {
        final var subject = new Review("11-06-2002 22:57:00");
        assertEquals("11-06-2002 22:57:00", subject.getDataTime());
    }
    @Test
    void ensureStatusIsSet() {
        final var subject = new Review("Pending");
        assertEquals("Pending", subject.getStatus());
    }
    /*
    @Test
    void ensureProductIdIsSet() {
        final var subject = new Review("1L");
        assertEquals("1L", subject.getProductId());
    }
    @Test
    void ensureCostumerIsSet() {
        final var subject = new Review("5L");
        assertEquals("5L", subject.getCustomerId());
    }*/
    @Test
    void ensurePatchUpVote() {
        final var patch = new Review("Samsung");
        patch.setUpVote(50);

        final var subject = new Review("name");
        subject.setUpVote(99);

        subject.applyPatch(patch, 0);

        assertEquals(Integer.valueOf(50), subject.getUpVote());
    }
    @Test
    void ensurePatchDownVote() {
        final var patch = new Review("Samsung");
        patch.setDownVote(50);

        final var subject = new Review("name");
        subject.setDownVote(99);

        subject.applyPatch(patch, 0);

        assertEquals(Integer.valueOf(50), subject.getDownVote());
    }
    /*
    @Test
    void ensurePatchStatus() {
        final var patch = new Review("Samsung");
        patch.setStatus("Pending");

        final var subject = new Review("name");
        subject.setStatus("Approved");

        subject.applyPatch(patch, 0);

        assertEquals(patch, subject);
    }*/

}
