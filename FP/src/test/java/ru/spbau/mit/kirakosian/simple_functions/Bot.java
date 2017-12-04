package ru.spbau.mit.kirakosian.simple_functions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kirakosian.Predicate;

public class Bot extends Predicate<Object> {


    @Override
    public Boolean apply(@NotNull final Object o) {
        //noinspection ConstantConditions
        return o.equals(false);
    }
}
