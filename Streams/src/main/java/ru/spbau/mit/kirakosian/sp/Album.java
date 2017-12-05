package ru.spbau.mit.kirakosian.sp;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Album {

    private final String name;
    @NotNull
    private final List<Track> tracks;
    private final Artist artist;

    public Album(final Artist artist, final String name, final Track... tracks) {
        this.name = name;
        this.tracks = Arrays.asList(tracks);
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    @NotNull
    public List<Track> getTracks() {
        return tracks;
    }

    public Artist getArtist() {
        return artist;
    }
}