package com.xyzcorp.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlbumTest {

    @Test
    public void testToString() {
        Album album = new Album("The Lexicon of Love", "ABC", 1982);
        assertThat(album.toString()).isEqualTo("Album{The Lexicon of Love, ABC, 1982}");
    }

    @Test
    public void testEquals() {
        Album album1 = new Album("The Lexicon of Love", "ABC", 1982);
        Album album2 = new Album("The Lexicon of Love", "ABC", 1982);
        assertThat(album1).isEqualTo(album2);
    }

    @Test
    public void testNotEquals() {
        Album album1 = new Album("The Lexicon of Love", "ABC", 1982);
        Album album2 = new Album("Prisoner of Love", "James Brown", 1963);
        assertThat(album1).isNotEqualTo(album2);
    }

}
