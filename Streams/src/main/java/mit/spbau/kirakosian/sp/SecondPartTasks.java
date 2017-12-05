package mit.spbau.kirakosian.sp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(@NotNull final List<String> paths, @NotNull final CharSequence sequence) {
        return paths.stream().flatMap((path) -> {
            try {
                return Files.lines(Paths.get(path));
            } catch (IOException e) {
                //Just skip this file
                return Stream.empty();
            }
        }).filter((s) -> s.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        final int TIMES = 10000;
        final Random random = new Random();
        final double score = (double) random.doubles().limit(TIMES).filter((x) -> {
            final double y = random.nextDouble();
            return (x - 0.5)* (x - 0.5) + (y - 0.5) * (y - 0.5) <= 0.5 * 0.5;
        }
        ).count();
        return score / TIMES;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(@NotNull final Map<String, List<String>> compositions) {
        return compositions.entrySet().stream().max(Comparator.comparing(
                (p) -> p.getValue().stream().collect(Collectors.joining()).length()
        )).map(Map.Entry::getKey).orElse(null);
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(@NotNull final List<Map<String, Integer>> orders) {
        return orders.stream().flatMap((m) -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a + b));
    }
}