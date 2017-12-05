package ru.spbau.mit.kirakosian.sp;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(@NotNull final Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
        //        throw new UnsupportedOperationException();
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(@NotNull final Stream<Album> albums) {
        return albums.map(Album::getName).sorted().collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(@NotNull final Stream<Album> albums) {
        return albums.map(Album::getTracks).flatMap(List::stream)
                .map(Track::getName).sorted().collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(@NotNull final Stream<Album> albums) {
        return albums.filter(album ->
                album.getTracks().stream().filter(track -> track.getRating() > 95).count() > 0
        ).sorted(Comparator.comparing(Album::getName)).collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(@NotNull final Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(@NotNull final Stream<Album> albums) {
        return albums.collect(Collectors.toMap(
                Album::getArtist,
                (a) -> {
                    final List<String> l = new ArrayList<>();
                    l.add(a.getName());
                    return l;
                    },
                (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(@NotNull final Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(
                Function.identity()))
                .values().stream().filter(s -> s.size() > 1).count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(@NotNull final Stream<Album> albums) {
        return albums.min(Comparator.comparing(
                a -> a.getTracks().stream().map(Track::getRating)
                .max(Comparator.naturalOrder()).orElse(0)
        ));
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(@NotNull final Stream<Album> albums) {
        return albums.sorted(Comparator.comparing(
                a -> -a.getTracks().stream()
                .collect(Collectors.averagingInt(Track::getRating))
        )).collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(@NotNull final IntStream stream, final int modulo) {
        return stream.reduce(1, (a, b) -> (a * b) % modulo);
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(@NotNull final String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining(", ", "<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(@NotNull final Stream<?> s, @NotNull final Class<R> clazz) {
        return s.filter(clazz::isInstance).map(clazz::cast);
    }
}