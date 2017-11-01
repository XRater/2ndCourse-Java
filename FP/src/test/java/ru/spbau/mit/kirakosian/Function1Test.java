package ru.spbau.mit.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.simple_functions.Const42;
import ru.spbau.mit.kirakosian.simple_functions.Id;

import static org.junit.jupiter.api.Assertions.*;

public class Function1Test {

    @Test
    public void testApplyConst42() {
        final Function1<Integer, Object> const42 = new Const42<>();
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

    @Test
    public void testComposeIdIdBaseDerived() {
        final Function1<Base, Base> idBase = new Id<>();
        final Function1<Derived, Derived> idDerived = new Id<>();
        final Function1<Derived, Derived> idCompDD = idDerived.compose(idDerived);
        final Function1<Base, Base> idCompBB = idBase.compose(idBase);
        final Function1<Base, Derived> idCompBD = idDerived.compose(idBase);
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
