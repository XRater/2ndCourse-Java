package ru.spbau.mit.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.simple_functions.*;

import static org.junit.jupiter.api.Assertions.*;

public class PredicateTest {

    @Test
    public void testApply() {
        final IsZero isZero = new IsZero();
        assertTrue(isZero.apply(0));
        assertFalse(isZero.apply(1));
        assertFalse(isZero.apply(42));
    }

    @Test
    public void testConstants() {
        assertTrue(Predicate.ALWAYS_TRUE.apply("hello"));
        assertTrue(Predicate.ALWAYS_TRUE.apply(false));
        assertTrue(Predicate.ALWAYS_TRUE.apply(true));
        assertTrue(Predicate.ALWAYS_TRUE.apply(new Object()));
        assertTrue(Predicate.ALWAYS_TRUE.apply(null));

        assertFalse(Predicate.ALWAYS_FALSE.apply("hello"));
        assertFalse(Predicate.ALWAYS_FALSE.apply(false));
        assertFalse(Predicate.ALWAYS_FALSE.apply(true));
        assertFalse(Predicate.ALWAYS_FALSE.apply(new Object()));
        assertFalse(Predicate.ALWAYS_FALSE.apply(null));
    }

    @Test
    public void testNot() {
        final IsZero isZero = new IsZero();
        assertFalse(isZero.not().apply(0));
        assertTrue(isZero.not().apply(1));
        assertTrue(isZero.not().apply(2));
    }

    @Test
    public void testAndInteger() {
        final IsOdd isOdd = new IsOdd();
        final IsPositive isPositive = new IsPositive();

        assertTrue(isOdd.and(isPositive).apply(1));
        assertFalse(isOdd.and(isPositive).apply(-1));
        assertFalse(isOdd.and(isPositive).apply(2));
        assertFalse(isOdd.and(isPositive).apply(-2));

        assertTrue(isPositive.and(isOdd).apply(1));
        assertFalse(isPositive.and(isOdd).apply(2));
        assertFalse(isPositive.and(isOdd).apply(-1));
        assertFalse(isPositive.and(isOdd).apply(-2));
    }

    @Test
    public void testAndConstants() {
        final IsZero isZero = new IsZero();
        assertTrue(isZero.and(Predicate.ALWAYS_TRUE).apply(0));
        assertFalse(isZero.and(Predicate.ALWAYS_TRUE).apply(1));

        assertFalse(isZero.and(Predicate.ALWAYS_FALSE).apply(0));
        assertFalse(isZero.and(Predicate.ALWAYS_FALSE).apply(1));
    }

    @Test
    public void testAndBaseDerived() {
        final Tru<Derived> trueD = new Tru<>();
        final Tru<Base> trueB = new Tru<>();
        assertTrue(trueD.and(trueB).apply(new Derived()));
        assertTrue(trueD.and(trueD).apply(new Derived()));
        assertTrue(trueB.and(trueB).apply(new Base()));
        assertTrue(trueB.and(trueB).apply(new Derived()));
    }

    @Test
    public void testOrInteger() {
        final IsOdd isOdd = new IsOdd();
        final IsPositive isPositive = new IsPositive();

        assertTrue(isOdd.or(isPositive).apply(1));
        assertTrue(isOdd.or(isPositive).apply(-1));
        assertTrue(isOdd.or(isPositive).apply(2));
        assertFalse(isOdd.or(isPositive).apply(-2));

        assertTrue(isPositive.or(isOdd).apply(1));
        assertTrue(isPositive.or(isOdd).apply(2));
        assertTrue(isPositive.or(isOdd).apply(-1));
        assertFalse(isPositive.or(isOdd).apply(-2));
    }

    @Test
    public void testOrConstants() {
        final IsZero isZero = new IsZero();
        assertTrue(isZero.or(Predicate.ALWAYS_TRUE).apply(0));
        assertTrue(isZero.or(Predicate.ALWAYS_TRUE).apply(1));

        assertTrue(isZero.or(Predicate.ALWAYS_FALSE).apply(0));
        assertFalse(isZero.or(Predicate.ALWAYS_FALSE).apply(1));
    }

    @Test
    public void testOrBaseDerived() {
        final Fls<Derived> falseD = new Fls<>();
        final Fls<Base> falseB = new Fls<>();
        assertFalse(falseD.or(falseB).apply(new Derived()));
        assertFalse(falseD.or(falseD).apply(new Derived()));
        assertFalse(falseB.or(falseB).apply(new Base()));
        assertFalse(falseB.or(falseB).apply(new Derived()));
    }

    @Test
    public void testAndLazy() {
        final Bot bot = new Bot();
        assertFalse(Predicate.ALWAYS_FALSE.and(bot).apply(null));
    }

    @Test
    public void testOrLazy() {
        final Bot bot = new Bot();
        assertTrue(Predicate.ALWAYS_TRUE.or(bot).apply(null));
    }

    private static class Base {}

    private static class Derived extends Base {}

}