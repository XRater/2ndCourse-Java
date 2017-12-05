package mit.spbau.kirakosian.sp;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(final Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
        //        throw new UnsupportedOperationException();
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(final Stream<Album> albums) {
        return albums.map(Album::getName).sorted().collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(final Stream<Album> albums) {
        return albums.map(Album::getTracks).flatMap(List::stream)
                .map(Track::getName).sorted().collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(final Stream<Album> albums) {
        return albums.filter(album ->
                album.getTracks().stream().filter(track -> track.getRating() > 95).count() > 0
        ).sorted(Comparator.comparing(Album::getName)).collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(final Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(final Stream<Album> albums) {
//        return albums.collect(Collectors.groupingBy());
        return null;
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(final Stream<Album> albums) {
        throw new UnsupportedOperationException();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(final Stream<Album> albums) {
        throw new UnsupportedOperationException();
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(final Stream<Album> albums) {
        throw new UnsupportedOperationException();
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(final IntStream stream, final int modulo) {
        throw new UnsupportedOperationException();
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(final String... strings) {
        throw new UnsupportedOperationException();
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(final Stream<?> s, final Class<R> clazz) {
        throw new UnsupportedOperationException();
    }
}