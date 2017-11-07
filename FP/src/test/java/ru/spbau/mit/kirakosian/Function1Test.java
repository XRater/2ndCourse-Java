package ru.spbau.mit.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.simple_functions.Const42;
import ru.spbau.mit.kirakosian.simple_functions.Id;
import ru.spbau.mit.kirakosian.simple_functions.Negate;

import static org.junit.jupiter.api.Assertions.*;

public class Function1Test {

    @Test
    public void testApplyConst42() {
        final Function1<Object, Integer> const42 = new Const42<>();
        assertEquals((Integer) 42, const42.apply(1));
        assertEquals((Integer) 42, const42.apply(new Object()));
        assertEquals((Integer) 42, const42.apply(42));
        assertEquals((Integer) 42, const42.apply(null));
        assertEquals((Integer) 42, const42.apply("hello"));
        assertEquals((Integer) 42, const42.apply("43"));
    }

    @Test
    public void testApplyIdString() {
        final Function1<String, String> id = new Id<>();
        assertEquals("hello", id.apply("hello"));
        assertEquals("1", id.apply("1"));
        assertEquals("", id.apply(""));
        assertEquals(null, id.apply(null));
    }

    @Test
    public void testApplyIdObject() {
        final Function1<Object, Object> id = new Id<>();
        final Object o =new Object();
        assertEquals(o, id.apply(o));
        assertEquals(null, id.apply(null));
    }

    @Test
    public void testApplyIdPoint() {
        final Function1<Point, Point> id = new Id<>();
        assertEquals(null, id.apply(null));
        assertEquals(new Point(0, 0), id.apply(new Point(0, 0)));
        assertEquals(new Point(0, 1), id.apply(new Point(0, 1)));
        assertEquals(new Point(1, 0), id.apply(new Point(1, 0)));
        assertEquals(new Point(42, 2), id.apply(new Point(42, 2)));
    }

    @Test
    public void testApplyIdBaseDerived() {
        final Function1<Base, Base> id = new Id<>();
        id.apply(new Derived());
        id.apply(new Base());
    }

    @Test
    public void testComposeIdId() {
        final Function1<String, String> id = new Id<>();
        final Function1<String, String> idId = id.compose(id);
        assertEquals("string", idId.apply("string"));
        assertEquals("", idId.apply(""));
        assertEquals(null, idId.apply(null));
    }

    @SuppressWarnings("unused")
    @Test
    public void testComposeIdIdBaseDerived() {
        final Function1<Base, Base> idBase = new Id<>();
        final Function1<Derived, Derived> idDerived = new Id<>();
        final Function1<Derived, Derived> idCompDD = idDerived.compose(idDerived);
        final Function1<Base, Base> idCompBB = idBase.compose(idBase);
        final Function1<Derived, Base> idCompDB = idDerived.compose(idBase);
    }

    @Test
    public void testComposeNegateIdNegate() {
        final Negate neg = new Negate();
        final Id<Integer> id = new Id<>();
        final Function1<Integer, Integer> nin = neg.compose(id.compose(neg));
        assertEquals((Integer) 1, nin.apply(1));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testComposeOrder() {
        final Const42 const42 = new Const42();
        final Negate neg = new Negate();
        assertEquals(-42, const42.compose(neg).apply(1));
        assertEquals(42, neg.compose(const42).apply(1));
    }

    @SuppressWarnings("WeakerAccess")
    private static class Point {
        int x;
        int y;

        public Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Point) {
                final Point p = (Point) obj;
                return x == p.x && y == p.y;
            }
            return false;
        }
    }

    private static class Base {}

    private static class Derived extends Base {}
}
