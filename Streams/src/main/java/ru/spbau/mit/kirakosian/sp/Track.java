package ru.spbau.mit.kirakosian.sp;

@SuppressWarnings("WeakerAccess")
public class Track {

    private final String name;
    private final int rating;

    public Track(final String name, final int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }
}