package com.xyzcorp.models;

import java.util.Objects;

public class Album {
    private String name;
    private String artist;
    private int year;
    private int id;

    public Album(String name, String artist, int year) {
        this.name = name;
        this.artist = artist;
        this.year = year;
    }

    public Album(int id, String name, String artist, int year) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Album{" + getName() + ", " + getArtist() + ", " + getYear() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Album)) return false;
        Album other = (Album) obj;
        return Objects.equals(this.name, other.name) &&
                Objects.equals(this.artist, other.artist) &&
                this.year == other.year;
    }
}
