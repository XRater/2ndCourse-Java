package ru.spbau.mit.kirakosian.sp;

@SuppressWarnings("WeakerAccess")
public class Artist {

    private final String name;

    public Artist(final String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }
}