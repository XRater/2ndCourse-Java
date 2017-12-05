package mit.spbau.kirakosian.sp;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Album {

    private final String name;
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

    public List<Track> getTracks() {
        return tracks;
    }

    public Artist getArtist() {
        return artist;
    }
}